package api;

import api.dto.in.CreateCandidate;
import api.dto.in.UpdateCandidate;
import api.dto.out.CandidateOut;
import domain.CandidateService;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CandidateApi {
    private final CandidateService service;

    public CandidateApi(CandidateService service) {
        this.service = service;
    }

    public void create(CreateCandidate dto) {
        service.save(dto.toDomain());
    }

    public CandidateOut update(String id, UpdateCandidate dto) {
        service.save(dto.toDomain(id));
        return CandidateOut.fromDomain(service.findById(id));
    }

    public List<CandidateOut> list() {
        return service.findAll().stream().map(CandidateOut::fromDomain).toList();
    }
}
