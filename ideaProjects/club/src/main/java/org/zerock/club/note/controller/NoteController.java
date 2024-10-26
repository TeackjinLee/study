package org.zerock.club.note.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.club.note.dto.NoteDTO;
import org.zerock.club.note.service.NoteService;

@RestController
@Log4j2
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService; // final

    @PostMapping(value = "")
    public ResponseEntity<Long> register(@RequestBody NoteDTO noteDTO) {
        log.info("------------------------------register----------------------------------");
        log.info(noteDTO);

        Long num = noteService.register(noteDTO);

        return new ResponseEntity<>(num, HttpStatus.OK);
    }

}
