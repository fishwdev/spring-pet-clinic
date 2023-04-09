package springframework.springpetclinic.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import springframework.springpetclinic.model.Owner;
import springframework.springpetclinic.services.OwnerService;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

	@Mock
	OwnerService ownerService;

	@InjectMocks
	OwnerController ownerController;

	Set<Owner> owners;

	MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		owners = new HashSet<>();

		Owner ownerOne = new Owner();
		ownerOne.setId(1L);

		Owner ownerTwo = new Owner();
		ownerOne.setId(2L);

		owners.add(ownerOne);
		owners.add(ownerTwo);

		mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
	}

	@Test
	void listOwners() throws Exception {
		when(ownerService.findAll()).thenReturn(owners);

		mockMvc.perform(get("/owners")).andExpect(status().isOk()).andExpect(view().name("owners/index"))
				.andExpect(model().attribute("owners", hasSize(2)));

		mockMvc.perform(get("/owners/index")).andExpect(status().isOk()).andExpect(view().name("owners/index"))
				.andExpect(model().attribute("owners", hasSize(2)));
	}

	@Test
	void findOwners() throws Exception {
		mockMvc.perform(get("/owners/find")).andExpect(status().isOk()).andExpect(view().name("tobedone"));

		verifyNoInteractions(ownerService);
	}

	@Test
	void displayOwner() throws Exception {
		Owner ownerOne = new Owner();
		ownerOne.setId(1L);

		when(ownerService.findById(anyLong())).thenReturn(ownerOne);

		mockMvc.perform(get("/owners/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("/owners/ownerDetails"))
				.andExpect(model().attribute("owner", hasProperty("id", is(1L))));
	}

}