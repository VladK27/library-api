package ru.karelin.project.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.karelin.project.models.Reader;
import ru.karelin.project.payload.requests.ReaderRequest;
import ru.karelin.project.payload.response.ApiResponse;
import ru.karelin.project.payload.response.PagedResponse;
import ru.karelin.project.payload.response.ReaderResponse;
import ru.karelin.project.services.ReaderService;
import ru.karelin.project.validators.ReaderValidator;

import static ru.karelin.project.utility.AppConstants.DEFAULT_PAGE_NUMBER;
import static ru.karelin.project.utility.AppConstants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping("/readers")
public class ReaderController {
    private final ReaderService readerService;
    private final ModelMapper modelMapper;
    private final ReaderValidator readerValidator;

    @Autowired
    public ReaderController(ReaderService readerService, ModelMapper modelMapper, ReaderValidator readerValidator) {
        this.readerService = readerService;
        this.modelMapper = modelMapper;
        this.readerValidator = readerValidator;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<ReaderResponse>> getAllReaders(
            @RequestParam(name = "page", required = false, defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE) int size
    ){
        PagedResponse<ReaderResponse> response = readerService.getAll(page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReaderResponse> getReaderById(@PathVariable Long id){
        ReaderResponse response = readerService.getById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReaderResponse> addReader(@Valid @RequestBody ReaderRequest readerRequest){
        Reader reader = modelMapper.map(readerRequest, Reader.class);
        readerValidator.validate(reader);

        Reader newReader = readerService.addReader(reader);

        return new ResponseEntity<>(modelMapper.map(newReader, ReaderResponse.class), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReaderResponse> updateReader(
            @PathVariable("id") Long id,
            @Valid @RequestBody ReaderRequest readerRequest)
    {
        Reader reader = modelMapper.map(readerRequest, Reader.class);
        reader.setId(id);
        readerValidator.validate(reader);

        Reader newReader = readerService.updateReader(reader);

        return new ResponseEntity<>(modelMapper.map(newReader, ReaderResponse.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteReader(@PathVariable Long id){
        readerService.deleteById(id);

        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE, "reader with id: " + id + " has been deleted");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
