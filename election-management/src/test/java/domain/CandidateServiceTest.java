package domain;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class CandidateServiceTest {

    @Inject
    CandidateService service;

    @InjectMock
    CandidateRepository repository;

    @Test
    void save() {
        Candidate candidate = Instancio.create(Candidate.class);
//        Candidate candidate = new Candidate("", Optional.empty(), "", "", "", Optional.empty(),Optional.empty());
        service.save(candidate);
        verify(repository).save(candidate);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll() {
        List<Candidate> candidates = Instancio.stream(Candidate.class).limit(10).toList();
        when(repository.findAll()).thenReturn(candidates);
        var results = service.findAll();
        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
        assertEquals(candidates, results);
    }

    @Test
    void findById_whenCandidateIsFound_returnsCandidate() {
        Candidate candidate = Instancio.create(Candidate.class);
        when(repository.findById(candidate.id())).thenReturn(Optional.of(candidate));
        Candidate result = service.findById(candidate.id());
        verify(repository).findById(candidate.id());
        verifyNoMoreInteractions(repository);
        assertEquals(candidate, result);

    }

    @Test
    void findById_whenCandidateIsNotFound_throwsException() {
        Candidate candidate = Instancio.create(Candidate.class);
        when(repository.findById(candidate.id())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.findById(candidate.id()));

    }
}