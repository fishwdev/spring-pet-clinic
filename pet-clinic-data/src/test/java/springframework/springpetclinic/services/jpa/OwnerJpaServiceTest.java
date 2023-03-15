package springframework.springpetclinic.services.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springframework.springpetclinic.model.Owner;
import springframework.springpetclinic.repositories.OwnerRepository;
import springframework.springpetclinic.repositories.PetRepository;
import springframework.springpetclinic.repositories.PetTypeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerJpaServiceTest {

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerJpaService ownerJpaService;

    Long ownerId = 1L;
    String lastName = "Smith";

    Owner owner;

    @BeforeEach
    void setUp() {
        owner = new Owner();
        owner.setId(ownerId);
        owner.setLastName(lastName);
    }

    @Test
    void findAll() {
        Set<Owner> owners = new HashSet<>();
        owners.add(new Owner());
        owners.add(new Owner());

        when(ownerRepository.findAll()).thenReturn(owners);

        Set<Owner> returnedOwners = ownerJpaService.findAll();

        assertNotNull(returnedOwners);
        assertEquals(2, returnedOwners.size());
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));

        Owner returnedOwner = ownerJpaService.findById(ownerId);

        assertEquals(ownerId, returnedOwner.getId());
    }

    @Test
    void findByNonExistingId() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

        Owner returnedOwner = ownerJpaService.findById(ownerId);

        assertNull(returnedOwner);
    }

    @Test
    void save() {
        when(ownerRepository.save(any())).thenReturn(owner);
        Owner returnedOwner = ownerJpaService.save(owner);

        assertNotNull(returnedOwner);

        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {
        ownerJpaService.delete(owner);

        verify(ownerRepository).delete(any());
    }

    @Test
    void deleteById() {
        ownerJpaService.deleteById(ownerId);

        verify(ownerRepository).deleteById(anyLong());
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(any())).thenReturn(owner);

        Owner returnedOwner = ownerJpaService.findByLastName(lastName);

        assertEquals(lastName, returnedOwner.getLastName());

        verify(ownerRepository).findByLastName(any());
    }
}