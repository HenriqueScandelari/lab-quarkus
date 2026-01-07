package infrastructure.resources;

import api.CandidateApi;
import api.dto.in.CreateCandidate;
import api.dto.out.CandidateOut;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.*;

@QuarkusTest
@TestHTTPEndpoint(CandidateResource.class)
class CandidateResourceTest {
    @InjectMock
    CandidateApi api;

    @Test
    void create() {
        CreateCandidate in = Instancio.create(CreateCandidate.class);

        given().contentType(MediaType.APPLICATION_JSON).body(in)
                .when().post()
                .then().statusCode(Response.Status.CREATED.getStatusCode());

        verify(api).create(in);
        verifyNoMoreInteractions(api);
    }

    @Test
    void list() {
        List<CandidateOut> list = Instancio.stream(CandidateOut.class).limit(4).toList();
        when(api.list()).thenReturn(list);
        var response = given().when().get()
                .then().statusCode(Response.Status.OK.getStatusCode())
                .extract().as(CandidateOut[].class);
        verify(api).list();
        verifyNoMoreInteractions(api);
        Assertions.assertEquals(list, Arrays.stream(response).toList());
    }
}