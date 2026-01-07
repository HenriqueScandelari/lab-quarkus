package api;

import api.dto.in.CreateCandidate;
import api.dto.in.UpdateCandidate;
import api.dto.out.CandidateOut;
import domain.Candidate;
import domain.CandidateService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@QuarkusTest
class CandidateApiTest {
    @Inject
    CandidateApi api;
    @InjectMock
    CandidateService service;

    @Test
    void create() {
        CreateCandidate dto = Instancio.create(CreateCandidate.class);
        ArgumentCaptor<Candidate> captor = ArgumentCaptor.forClass(Candidate.class);
        api.create(dto);

        verify(service).save(captor.capture());
        verifyNoMoreInteractions(service);

        Candidate candidate = captor.getValue();
        Assertions.assertEquals(dto.email(), candidate.email());
        Assertions.assertEquals(dto.familyName(), candidate.familyName());
        Assertions.assertEquals(dto.givenName(), candidate.givenName());
        Assertions.assertEquals(dto.jobTitle(), candidate.jobTitle());
        Assertions.assertEquals(dto.phone(), candidate.phone());
        Assertions.assertEquals(dto.photo(), candidate.photo());

    }

    @Test
    void update() {
        String id = UUID.randomUUID().toString();
        UpdateCandidate dto = Instancio.create(UpdateCandidate.class);
        Candidate candidate = dto.toDomain(id);

        ArgumentCaptor<Candidate> captor = ArgumentCaptor.forClass(Candidate.class);

        when(service.findById(id)).thenReturn(candidate);

        var response = api.update(id, dto);

        verify(service).save(captor.capture());
        verify(service).findById(id);
        verifyNoMoreInteractions(service);

        Assertions.assertEquals(CandidateOut.fromDomain(candidate),
                response);

    }

    @Test
    void list() {
        var candidates = Instancio.stream(Candidate.class).limit(10).toList();
        when(service.findAll()).thenReturn(candidates);
        var response = api.list();
        verify(service).findAll();
        verifyNoMoreInteractions(service);
        Assertions.assertEquals(candidates.stream().map(CandidateOut::fromDomain).toList(), response);

    }

}