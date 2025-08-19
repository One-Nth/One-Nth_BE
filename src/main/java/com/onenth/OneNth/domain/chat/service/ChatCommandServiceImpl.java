package com.onenth.OneNth.domain.chat.service;

import com.onenth.OneNth.domain.chat.converter.ChatConverter;
import com.onenth.OneNth.domain.chat.dto.ChatResponseDTO;
import com.onenth.OneNth.domain.chat.entity.ChatMessage;
import com.onenth.OneNth.domain.chat.entity.ChatRoom;
import com.onenth.OneNth.domain.chat.entity.ChatRoomMember;
import com.onenth.OneNth.domain.chat.entity.enums.ChatRoomType;
import com.onenth.OneNth.domain.chat.repository.ChatMessageRepository;
import com.onenth.OneNth.domain.chat.repository.ChatRoomMemberRepository;
import com.onenth.OneNth.domain.chat.repository.ChatRoomRepository;
import com.onenth.OneNth.domain.chat.dto.ChatMessageDTO;
import com.onenth.OneNth.domain.member.entity.Member;
import com.onenth.OneNth.domain.member.entity.Report;
import com.onenth.OneNth.domain.member.entity.enums.ReportType;
import com.onenth.OneNth.domain.member.repository.ReportRepository;
import com.onenth.OneNth.domain.member.repository.memberRepository.MemberRepository;
import com.onenth.OneNth.domain.product.entity.enums.ItemType;
import com.onenth.OneNth.global.apiPayload.code.status.ErrorStatus;
import com.onenth.OneNth.global.apiPayload.exception.handler.ChatHandler;
import com.onenth.OneNth.global.apiPayload.exception.handler.MemberHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static com.onenth.OneNth.domain.alert.converter.AlertConverter.toAlert;
import static com.onenth.OneNth.domain.alert.entity.AlertType.REVIEW;
import static com.onenth.OneNth.domain.alert.fcm.FcmClient.sendNotification;

@Service
@RequiredArgsConstructor
public class ChatCommandServiceImpl implements ChatCommandService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final MemberRepository memberRepository;
    private final ChatMessageRepository chatMessageRepository;

    private final SimpMessagingTemplate messagingTemplate;
    private final ReportRepository reportRepository;

    public ChatResponseDTO.ChatRoomResponseDTO getChatRoomName(Long memberId, Long targetMemberId, ChatRoomType chatRoomType) {
        if (memberId == targetMemberId) {
            throw new ChatHandler(ErrorStatus.CHAT_ROOM_NOT_FOUND);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        Member targetMember = memberRepository.findById(targetMemberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        String chatRoomName = generateChatRoomName(memberId, targetMemberId, chatRoomType);

        return chatRoomRepository.findByName(chatRoomName)
                .map(ChatConverter::toChatRoomResponseDTO)
                .orElseGet(() -> {
                    ChatRoom newChatRoom = chatRoomRepository.save(ChatRoom.builder()
                            .name(chatRoomName)
                            .chatRoomType(chatRoomType)
                            .member1(member)
                            .member2(targetMember)
                            .build());

                    Stream.of(member, targetMember)
                            .forEach(m -> chatRoomMemberRepository.save(ChatRoomMember.builder()
                                    .member(m)
                                    .chatRoom(newChatRoom)
                                    .build()));
                    return ChatConverter.toChatRoomResponseDTO(newChatRoom);
                });
    }

    @Override
    @Transactional
    public void leaveChatRoom(Long memberId, Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findWithChatRoomMembersById(chatRoomId)
                .orElseThrow(() -> new ChatHandler(ErrorStatus.CHAT_ROOM_NOT_FOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        ChatRoomMember chatRoomMember = chatRoom.getChatRoomMembers().stream()
                .filter(crm -> crm.getMember().getId().equals(memberId))
                .findFirst()
                .orElseThrow(() -> new ChatHandler(ErrorStatus._FORBIDDEN));

        member.getChatRoomMembers().remove(chatRoomMember);
    }

    @Override
    @Transactional
    public void createMessage(String roomName, ChatMessageDTO.MessageDTO chatMessageDTO) {
        Member member = memberRepository.findById(chatMessageDTO.getSendMemberId())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        ChatRoom chatRoom = chatRoomRepository.findByName(roomName)
                .orElseThrow(() -> new ChatHandler(ErrorStatus.CHAT_ROOM_NOT_FOUND));

        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .member(member)
                .content(chatMessageDTO.getContent())
                .build();
        chatMessageRepository.save(chatMessage);

        messagingTemplate.convertAndSend(
                "/sub/chat-rooms/" + chatRoom.getName(), chatMessageDTO);
    }

    @Override
    @Transactional
    public void reportMember(Long memberId, Long chatRoomId, ReportType reportType) {
        Member reporter = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ChatHandler(ErrorStatus.CHAT_ROOM_NOT_FOUND));

        Member reported = chatRoom.getMember1().getId().equals(memberId) 
                ? chatRoom.getMember2() 
                : chatRoom.getMember1();

        reportRepository.save(Report.builder()
                        .reportType(reportType)
                        .reporter(reporter)
                        .reported(reported).build());
    }

    private String generateChatRoomName(Long id1, Long id2, ChatRoomType type) {
        Long smaller = Math.min(id1, id2);
        Long larger = Math.max(id1, id2);
        return smaller + "-" + larger + "-" + type;
    }
}