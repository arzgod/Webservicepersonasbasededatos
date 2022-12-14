/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ufg.webservices.webservicepersonas.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sv.edu.ufg.webservices.webservicepersonas.entities.Personas;
import sv.edu.ufg.webservices.webservicepersonas.entities.dto.PersonasDTO;

/**
 *
 * @author diego
 */
@ApplicationScoped
public class PersonasRepository {

    @PersistenceContext(unitName = "personasPU")
    private EntityManager entityManager;

    public List<Personas> findAll() {
        return entityManager.createNamedQuery("Personas.findAll").getResultList();
    }

    public Personas findById(Long idPersona) {
        List<Personas> personas = entityManager.createNamedQuery("Personas.findByIdPersona").setParameter("idPersona", idPersona).getResultList();
        if (!personas.isEmpty()) {
            return personas.get(0);
        } else {
            return null;
        }
    }
    
    public Personas fromDTO2Entity(PersonasDTO dto){
        Personas p = new Personas();
        p.setNombres(dto.getNombres());
        p.setApellidos(dto.getApellidos());
        p.setEmail(dto.getEmail());
        p.setSexo(dto.getSexo());
        p.setFechadenacimiento(dto.getFechadenacimiento());
        return p;
    }
    
    @Transactional
    public void save(PersonasDTO dto){
        Personas p = fromDTO2Entity(dto);
        entityManager.persist(p);
        //entityManager.refresh(p);
    }
    
    @Transactional
    public void delete(Long idPersona){
        entityManager.remove(findById(idPersona));
    }
    
    @Transactional
    public void update(PersonasDTO dto, Long idPersona){
        Personas p = fromDTO2Entity(dto);
        p.setIdPersona(idPersona);
        entityManager.merge(p);
        //entityManager.refresh(p);
}
}
