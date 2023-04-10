package springframework.springpetclinic.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import springframework.springpetclinic.model.Owner;
import springframework.springpetclinic.services.OwnerService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

	/*
	@Test
	void listOwners() throws Exception {
		when(ownerService.findAll()).thenReturn(owners);

		mockMvc.perform(get("/owners")).andExpect(status().isOk()).andExpect(view().name("owners/index"))
				.andExpect(model().attribute("owners", hasSize(2)));

		mockMvc.perform(get("/owners/index")).andExpect(status().isOk()).andExpect(view().name("owners/index"))
				.andExpect(model().attribute("owners", hasSize(2)));
	}
	 */

	@Test
	void findOwners() throws Exception {
		mockMvc.perform(get("/owners/find"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/findOwners"))
				.andExpect(model().attributeExists("owner"));

		verifyNoInteractions(ownerService);
	}

	@Test
	void processFindFormReturnMany() throws Exception {
		when(ownerService.findAllByLastNameLike(anyString())).thenReturn(new ArrayList<>(owners));

		mockMvc.perform(get("/owners"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/ownersList"))
				.andExpect(model().attribute("listOwners", hasSize(2)));
	}

	@Test
	void processFindFormReturnOne() throws Exception {
		Owner ownerOne = new Owner();
		ownerOne.setId(1L);

		when(ownerService.findAllByLastNameLike(anyString())).thenReturn(Arrays.asList(ownerOne));

		mockMvc.perform(get("/owners"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/1"));
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

	@Test
	void initCreationForm() throws Exception {
		mockMvc.perform(get("/owners/new"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/createOrUpdateOwnerForm"))
				.andExpect(model().attributeExists("owner"));

		verifyNoInteractions(ownerService);
	}

	@Test
	void processCreationForm() throws Exception {
		Owner ownerOne = new Owner();
		ownerOne.setId(1L);

		when(ownerService.save(ArgumentMatchers.any())).thenReturn(ownerOne);

		mockMvc.perform(post("/owners/new"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/1"))
				.andExpect(model().hasNoErrors());

		verify(ownerService).save(ArgumentMatchers.any());
	}

	@Test
	void initUpdateOwnerForm() throws Exception {
		Owner ownerOne = new Owner();
		ownerOne.setId(1L);

		when(ownerService.findById(anyLong())).thenReturn(ownerOne);

		mockMvc.perform(get("/owners/1/edit"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/createOrUpdateOwnerForm"))
				.andExpect(model().attributeExists("owner"));

		verify(ownerService).findById(anyLong());
	}

	@Test
	void processUpdateOwnerForm() throws Exception {
		Owner ownerOne = new Owner();
		ownerOne.setId(1L);

		when(ownerService.save(ArgumentMatchers.any())).thenReturn(ownerOne);

		mockMvc.perform(post("/owners/1/edit"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/1"))
				.andExpect(model().hasNoErrors());

		verify(ownerService).save(ArgumentMatchers.any());
	}

}