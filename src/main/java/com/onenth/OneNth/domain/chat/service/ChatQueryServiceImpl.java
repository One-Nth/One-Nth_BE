package com.onenth.OneNth.domain.chat.service;

import com.onenth.OneNth.domain.chat.converter.ChatConverter;
import com.onenth.OneNth.domain.chat.dto.ChatResponseDTO;
import com.onenth.OneNth.domain.chat.entity.ChatMessage;
import com.onenth.OneNth.domain.chat.entity.ChatRoom;
import com.onenth.OneNth.domain.chat.entity.ChatRoomMember;
import com.onenth.OneNth.domain.chat.entity.enums.ChatRoomType;
import com.onenth.OneNth.domain.chat.repository.ChatRoomRepository;
import com.onenth.OneNth.domain.member.entity.Block;
import com.onenth.OneNth.domain.member.entity.Member;
import com.onenth.OneNth.domain.member.repository.memberRepository.MemberRepository;
import com.onenth.OneNth.domain.member.settings.block.repository.BlockRepository;
import com.onenth.OneNth.global.apiPayload.code.status.ErrorStatus;
import com.onenth.OneNth.global.apiPayload.exception.handler.ChatHandler;
import com.onenth.OneNth.global.apiPayload.exception.handler.MemberHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.onenth.OneNth.domain.chat.converter.ChatConverter.toChatRoomPreviewDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatQueryServiceImpl implements ChatQueryService {

    private final MemberRepository memberRepository;

    private final ChatRoomRepository chatRoomRepository;
    private final BlockRepository blockRepository;

    // 참여 중인 채팅방 조회
    @Override
    public List<ChatResponseDTO.ChatRoomPreviewDTO> getMyChatRoomList(Long memberId, ChatRoomType chatRoomType) {
        Member member = memberRepository.findWithChatRoomsById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        return member.getChatRoomMembers().stream()
                .map(ChatRoomMember::getChatRoom)
                .filter(chatRoom -> chatRoom.getChatRoomType().equals(chatRoomType))
                .map(chatRoom -> {
                    ChatMessage lastMessage = chatRoom.getChatMessages().stream()
                            .max(Comparator.comparing(ChatMessage::getCreatedAt))
                            .orElse(null);

                    Member opponent = chatRoom.getChatRoomMembers().stream()
                            .map(ChatRoomMember::getMember)
                            .filter(m -> !m.getId().equals(memberId))
                            .findFirst()
                            .orElse(null);

                    Long opponentId = opponent == null ? null : opponent.getId();

                    Optional<Block> isBlocked
                            = blockRepository.findByMemberAndBlockedMember(member, opponent);

                    boolean blocked = isBlocked.isPresent();

                    ChatResponseDTO.ChatRoomPreviewDTO dto = toChatRoomPreviewDTO(chatRoom, opponentId, blocked);

                    if (lastMessage != null) {
                        dto.setLastMessageContent(lastMessage.getContent());
                        dto.setLastMessageTime(lastMessage.getCreatedAt());
                    }

                    return dto;
                })
                .toList();
    }

    @Override
    public List<ChatResponseDTO.ChatMessageDTO> getMyChatMessageList(Long memberId, Long chatRoomID) {
        ChatRoom chatRoom = chatRoomRepository.findWithChatMessagesById(chatRoomID)
                .orElseThrow(()-> new ChatHandler(ErrorStatus.CHAT_ROOM_NOT_FOUND));

        boolean isParticipant = chatRoom.getChatRoomMembers().stream()
                .anyMatch(crm -> crm.getMember().getId().equals(memberId));
        if (!isParticipant) {
            throw new ChatHandler(ErrorStatus._FORBIDDEN);
        }

        return chatRoom.getChatMessages()
                .stream()
                .map(ChatConverter::toChatMessageDTO)
                .collect(Collectors.toList());
    }
}
