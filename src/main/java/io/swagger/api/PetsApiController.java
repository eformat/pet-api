package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-02-02T21:19:10.160+10:00")

@Controller
public class PetsApiController implements PetsApi {
    private final ObjectMapper objectMapper;

    @Autowired
    private Tracer tracer;

    private Map<String, Pet> pets = new HashMap<String, Pet>();

    public PetsApiController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<Void> petsDelete(@NotNull @ApiParam(value = "Pet Name", required = true) @Valid @RequestParam(value = "name", required = true) String name,
                                           @RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        tracer.addTag("Operation", "Delete pet");
        pets.remove(name);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<List<Pet>> petsGet(@RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        tracer.addTag("Operation", "Get pets");
        if (accept != null && accept.contains("application/json")) {
            String arrayToJson = objectMapper.writeValueAsString(new ArrayList<Pet>(pets.values()));
            return new ResponseEntity<List<Pet>>(objectMapper.readValue(arrayToJson, List.class), HttpStatus.OK);
        }
        return new ResponseEntity<List<Pet>>(HttpStatus.OK);
    }

    public ResponseEntity<Void> petsPost(@ApiParam(value = "") @Valid @RequestBody Pet body,
                                         @RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        tracer.addTag("Operation", "Create pet");
        pets.put(body.getName(), body);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

}

