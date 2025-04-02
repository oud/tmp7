package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.PmEntreprise;
import com.mycompany.myapp.repository.PmEntrepriseRepository;
import com.mycompany.myapp.service.dto.PmEntrepriseDTO;
import com.mycompany.myapp.service.mapper.PmEntrepriseMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.PmEntreprise}.
 */
@Service
@Transactional
public class PmEntrepriseService {

    private static final Logger LOG = LoggerFactory.getLogger(PmEntrepriseService.class);

    private final PmEntrepriseRepository pmEntrepriseRepository;

    private final PmEntrepriseMapper pmEntrepriseMapper;

    public PmEntrepriseService(PmEntrepriseRepository pmEntrepriseRepository, PmEntrepriseMapper pmEntrepriseMapper) {
        this.pmEntrepriseRepository = pmEntrepriseRepository;
        this.pmEntrepriseMapper = pmEntrepriseMapper;
    }

    /**
     * Save a pmEntreprise.
     *
     * @param pmEntrepriseDTO the entity to save.
     * @return the persisted entity.
     */
    public PmEntrepriseDTO save(PmEntrepriseDTO pmEntrepriseDTO) {
        LOG.debug("Request to save PmEntreprise : {}", pmEntrepriseDTO);
        PmEntreprise pmEntreprise = pmEntrepriseMapper.toEntity(pmEntrepriseDTO);
        pmEntreprise = pmEntrepriseRepository.save(pmEntreprise);
        return pmEntrepriseMapper.toDto(pmEntreprise);
    }

    /**
     * Update a pmEntreprise.
     *
     * @param pmEntrepriseDTO the entity to save.
     * @return the persisted entity.
     */
    public PmEntrepriseDTO update(PmEntrepriseDTO pmEntrepriseDTO) {
        LOG.debug("Request to update PmEntreprise : {}", pmEntrepriseDTO);
        PmEntreprise pmEntreprise = pmEntrepriseMapper.toEntity(pmEntrepriseDTO);
        pmEntreprise = pmEntrepriseRepository.save(pmEntreprise);
        return pmEntrepriseMapper.toDto(pmEntreprise);
    }

    /**
     * Partially update a pmEntreprise.
     *
     * @param pmEntrepriseDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PmEntrepriseDTO> partialUpdate(PmEntrepriseDTO pmEntrepriseDTO) {
        LOG.debug("Request to partially update PmEntreprise : {}", pmEntrepriseDTO);

        return pmEntrepriseRepository
            .findById(pmEntrepriseDTO.getId())
            .map(existingPmEntreprise -> {
                pmEntrepriseMapper.partialUpdate(existingPmEntreprise, pmEntrepriseDTO);

                return existingPmEntreprise;
            })
            .map(pmEntrepriseRepository::save)
            .map(pmEntrepriseMapper::toDto);
    }

    /**
     * Get all the pmEntreprises.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PmEntrepriseDTO> findAll() {
        LOG.debug("Request to get all PmEntreprises");
        return pmEntrepriseRepository.findAll().stream().map(pmEntrepriseMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one pmEntreprise by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PmEntrepriseDTO> findOne(Long id) {
        LOG.debug("Request to get PmEntreprise : {}", id);
        return pmEntrepriseRepository.findById(id).map(pmEntrepriseMapper::toDto);
    }

    /**
     * Delete the pmEntreprise by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete PmEntreprise : {}", id);
        pmEntrepriseRepository.deleteById(id);
    }
}
