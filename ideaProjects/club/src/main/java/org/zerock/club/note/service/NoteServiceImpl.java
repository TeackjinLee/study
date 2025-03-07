package org.zerock.club.note.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.mapping.Collection;
import org.springframework.stereotype.Service;
import org.zerock.club.entity.Note;
import org.zerock.club.note.dto.NoteDTO;
import org.zerock.club.repository.NoteRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;


    @Override
    public Long register(NoteDTO noteDTO) {

        Note note = dtoToEntity(noteDTO);

        log.info("===================");
        log.info(note);

        noteRepository.save(note);

        return note.getNum();
    }

    @Override
    public NoteDTO get(Long num) {

        Optional<Note> result = noteRepository.getWithWriter(num);

        return result.map(this::entityToDTO).orElse(null);

    }

    @Override
    public void modify(NoteDTO noteDTO) {
        Long num = noteDTO.getNum();

        Optional<Note> result = noteRepository.findById(num);

        if (result.isPresent()) {
           Note note = result.get();
           note.changeTitle(noteDTO.getTitle());
           note.changeContent(noteDTO.getContent());
           noteRepository.save(note);
        }
    }

    @Override
    public void remove(Long num) {
        noteRepository.deleteById(num);
    }

    @Override
    public List<NoteDTO> getAllWithWriter(String writerEmail) {

        List<Note> noteList = noteRepository.getList(writerEmail);

        return noteList.stream().map(this::entityToDTO).collect(Collectors.toList());
    }
}
