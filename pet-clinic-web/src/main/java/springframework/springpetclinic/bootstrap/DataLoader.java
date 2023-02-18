package springframework.springpetclinic.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springframework.springpetclinic.model.Owner;
import springframework.springpetclinic.model.Pet;
import springframework.springpetclinic.model.PetType;
import springframework.springpetclinic.model.Vet;
import springframework.springpetclinic.services.OwnerService;
import springframework.springpetclinic.services.PetTypeService;
import springframework.springpetclinic.services.VetService;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

	private final OwnerService ownerService;

	private final VetService vetService;

	private final PetTypeService petTypeService;

	public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
		this.ownerService = ownerService;
		this.vetService = vetService;
		this.petTypeService = petTypeService;
	}

	@Override
	public void run(String... args) throws Exception {

		PetType dog = new PetType();
		dog.setName("Dog");
		PetType savedDogPetType = petTypeService.save(dog);

		PetType cat = new PetType();
		cat.setName("Cat");
		PetType savedCatPetType = petTypeService.save(cat);

		System.out.println("Loaded PetTypes...");

		Owner owner1 = new Owner();
		owner1.setFirstName("Michael");
		owner1.setLastName("Weston");
		owner1.setAddress("123 Pickerel");
		owner1.setCity("Miami");
		owner1.setTelephone("1231231234");

		Pet mikesDog = new Pet();
		mikesDog.setName("Rosco");
		mikesDog.setPetType(savedDogPetType);
		mikesDog.setOwner(owner1);
		mikesDog.setBirthDate(LocalDate.now());
		owner1.getPets().add(mikesDog);

		ownerService.save(owner1);

		Owner owner2 = new Owner();
		owner2.setFirstName("Fiona");
		owner2.setLastName("Glenna");
		owner2.setAddress("123 Pickerel");
		owner2.setCity("Miami");
		owner2.setTelephone("1231231234");

		Pet fionasCat = new Pet();
		fionasCat.setName("Just Cat");
		fionasCat.setPetType(savedCatPetType);
		fionasCat.setOwner(owner2);
		fionasCat.setBirthDate(LocalDate.now());
		owner2.getPets().add(fionasCat);

		ownerService.save(owner2);

		System.out.println("Loaded Owners...");

		Vet vet1 = new Vet();
		vet1.setFirstName("Sam");
		vet1.setLastName("Axe");
		vetService.save(vet1);

		Vet vet2 = new Vet();
		vet2.setFirstName("Jessie");
		vet2.setLastName("Porter");
		vetService.save(vet2);

		System.out.println("Loaded Vets...");

	}

}
