package ru.karelin.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karelin.project.exceptions.ResourceNotFoundException;
import ru.karelin.project.models.Reader;
import ru.karelin.project.payload.response.PagedResponse;
import ru.karelin.project.payload.response.ReaderResponse;
import ru.karelin.project.repositories.ReaderRepository;
import static ru.karelin.project.utility.AppConstants.*;

import java.util.List;
import java.util.Optional;

@Service
public class ReaderService {
    private final ReaderRepository readerRepository;

    @Autowired
    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }


    public PagedResponse<ReaderResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, SURNAME, NAME);

        Page<Reader> readers = readerRepository.findAll(pageable);
        List<ReaderResponse> content = readers.getContent().stream()
                .map(r -> new ReaderResponse(
                        r.getId(), r.getName(), r.getSurname(), r.getYearOfBirth(), r.getEmail()
                )
                ).toList();

        return new PagedResponse<>(
                content, page, size, readers.getTotalPages(), readers.getTotalElements());
    }


    public ReaderResponse getById(Long id) {
        Optional<Reader> readerOptional = readerRepository.findById(id);

        if(readerOptional.isEmpty()){
            throw new ResourceNotFoundException(READER, ID, id.toString());
        }
        Reader reader = readerOptional.get();

        return new ReaderResponse(
                reader.getId(), reader.getName(), reader.getSurname(), reader.getYearOfBirth(), reader.getEmail());
    }

    public Optional<Reader> getByEmail(String email){
        return readerRepository.findByEmail(email);
    }

    @Transactional
    public Reader addReader(Reader reader) {
        return readerRepository.save(reader);
    }

    @Transactional
    //todo catch EntityNotFoundException, wrap in custom exception
    public Reader updateReader(Reader reader) {
        Reader readerToUpdate = readerRepository.getReferenceById(reader.getId());

        readerToUpdate.setName(reader.getName());
        readerToUpdate.setSurname(reader.getSurname());
        readerToUpdate.setEmail(reader.getEmail());
        readerToUpdate.setYearOfBirth(reader.getYearOfBirth());

        return readerRepository.save(readerToUpdate);
    }

    public void deleteById(Long id) {
        if(!readerRepository.existsById(id)){
            throw new ResourceNotFoundException(READER, ID, id.toString());
        }

        readerRepository.deleteById(id);
    }
}