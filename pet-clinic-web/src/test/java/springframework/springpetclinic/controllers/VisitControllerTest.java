package springframework.springpetclinic.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriTemplate;
import springframework.springpetclinic.model.Owner;
import springframework.springpetclinic.model.Pet;
import springframework.springpetclinic.model.PetType;
import springframework.springpetclinic.model.Visit;
import springframework.springpetclinic.services.OwnerService;
import springframework.springpetclinic.services.PetService;
import springframework.springpetclinic.services.VisitService;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    private static final String PETS_CREATE_OR_UPDATE_VISIT_FORM = "pets/createOrUpdateVisitForm";
    private static final String REDIRECT_OWNERS_1 = "redirect:/owners/{ownerId}";
    private static final String YET_ANOTHER_VISIT_DESCRIPTION = "yet another visit";

    @Mock
    PetService petService;

    @Mock
    VisitService visitService;

    @Mock
    OwnerService ownerService;

    @InjectMocks
    VisitController visitController;

    private MockMvc mockMvc;

    private final UriTemplate visitsUriTemplate = new UriTemplate("/owners/{ownerId}/pets/{petId}/visits/new");
    private final Map<String, String> uriVariables = new HashMap<>();
    private URI visitsUri;

    @BeforeEach
    void setUp() {
        Long ownerId = 1L;
        Long petId = 1L;

        Owner ownerOne = new Owner();
        ownerOne.setId(ownerId);
        ownerOne.setLastName("Doe");
        ownerOne.setFirstName("Joe");

        PetType petType = new PetType();
        petType.setName("Dog");

        Pet petOne = new Pet();
        petOne.setId(petId);
        petOne.setBirthDate(LocalDate.of(2018,11,13));
        petOne.setName("Cutie");
        petOne.setVisitSet(new HashSet<>());
        petOne.setOwner(ownerOne);
        petOne.setPetType(petType);


        when(petService.findById(anyLong())).thenReturn(petOne);

        uriVariables.clear();
        uriVariables.put("ownerId", ownerId.toString());
        uriVariables.put("petId", petId.toString());
        visitsUri = visitsUriTemplate.expand(uriVariables);

        mockMvc = MockMvcBuilders
                .standaloneSetup(visitController)
                .build();
    }

    @Test
    void initNewVisitForm() throws Exception {
        mockMvc.perform(get(visitsUri))
                .andExpect(status().isOk())
                .andExpect(view().name(PETS_CREATE_OR_UPDATE_VISIT_FORM))
        ;
    }


    @Test
    void processNewVisitForm() throws Exception {
        Visit visit = new Visit();
        visit.setPet(new Pet());
        visit.setDescription("");
        visit.setDate(LocalDate.of(2018,11,13));

        when(visitService.save(ArgumentMatchers.any())).thenReturn(visit);

        mockMvc.perform(post(visitsUri)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("date","2018-11-11")
                        .param("description", YET_ANOTHER_VISIT_DESCRIPTION))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_OWNERS_1));
    }
}