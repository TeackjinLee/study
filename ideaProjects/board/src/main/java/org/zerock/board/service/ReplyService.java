package org.zerock.board.service;

import org.zerock.board.dto.ReplyDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

import java.util.List;

public interface ReplyService {

    Long register (ReplyDTO replyDTO);

    List<ReplyDTO> getList(Long bno);

    void modify(ReplyDTO replyDTO);

    void remove(Long rno);

    // ReplyDTO를 Reply객체로 변환 Board객체의 처리가 수반됨
    default Reply dtoToEntity(ReplyDTO replyDTO) {
        Board board = Board.builder().bno(replyDTO.getBno()).build();

        return Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyer(replyDTO.getReplyer())
                .board(board)
                .build();
    }

    // Reply객체를 ReplyDTO로 변환 Board 객체가 필요하지 않으므로 게시물 번호만
    default ReplyDTO entityToDTO(Reply reply) {

        return ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();
    }
}
