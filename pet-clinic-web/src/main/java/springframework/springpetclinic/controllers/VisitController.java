package springframework.springpetclinic.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import springframework.springpetclinic.model.Owner;
import springframework.springpetclinic.model.Pet;
import springframework.springpetclinic.model.Visit;
import springframework.springpetclinic.services.OwnerService;
import springframework.springpetclinic.services.PetService;
import springframework.springpetclinic.services.VisitService;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.util.Map;

@Controller
class VisitController {

    private final VisitService visitService;
    private final PetService petService;
    private final OwnerService ownerService;

    public VisitController(VisitService visitService, PetService petService, OwnerService ownerService) {
        this.visitService = visitService;
        this.petService = petService;
        this.ownerService = ownerService;
    }

    @InitBinder
    public void dataBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");

        dataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDate.parse(text));
            }
        });
    }

    /**
     * Called before each and every @RequestMapping annotated method. 2 goals: - Make sure
     * we always have fresh data - Since we do not use the session scope, make sure that
     * Pet object always has an id (Even though id is not part of the form fields)
     * @param petId
     * @return Pet
     */
    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable("ownerId") Long ownerId, @PathVariable("petId") Long petId,
                                  Map<String, Object> model) {
        Owner owner = ownerService.findById(ownerId);
        Pet pet = petService.findById(petId);
        model.put("pet", pet);
        model.put("owner", owner);

        Visit visit = new Visit();
        pet.getVisitSet().add(visit);
        visit.setPet(pet);
        return visit;
    }

    // Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is
    // called
    @GetMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String initNewVisitForm() {
        return "pets/createOrUpdateVisitForm";
    }

    // Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is
    // called
    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processNewVisitForm(@ModelAttribute Owner owner, @PathVariable Long petId, @Valid Visit visit,
                                      BindingResult result) {
        if (result.hasErrors()) {
            return "pets/createOrUpdateVisitForm";
        }

        visitService.save(visit);
        return "redirect:/owners/{ownerId}";
    }

}