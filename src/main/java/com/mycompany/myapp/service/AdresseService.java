package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Adresse;
import com.mycompany.myapp.repository.AdresseRepository;
import com.mycompany.myapp.service.dto.AdresseDTO;
import com.mycompany.myapp.service.mapper.AdresseMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Adresse}.
 */
@Service
@Transactional
public class AdresseService {

    private static final Logger LOG = LoggerFactory.getLogger(AdresseService.class);

    private final AdresseRepository adresseRepository;

    private final AdresseMapper adresseMapper;

    public AdresseService(AdresseRepository adresseRepository, AdresseMapper adresseMapper) {
        this.adresseRepository = adresseRepository;
        this.adresseMapper = adresseMapper;
    }

    /**
     * Save a adresse.
     *
     * @param adresseDTO the entity to save.
     * @return the persisted entity.
     */
    public AdresseDTO save(AdresseDTO adresseDTO) {
        LOG.debug("Request to save Adresse : {}", adresseDTO);
        Adresse adresse = adresseMapper.toEntity(adresseDTO);
        adresse = adresseRepository.save(adresse);
        return adresseMapper.toDto(adresse);
    }

    /**
     * Update a adresse.
     *
     * @param adresseDTO the entity to save.
     * @return the persisted entity.
     */
    public AdresseDTO update(AdresseDTO adresseDTO) {
        LOG.debug("Request to update Adresse : {}", adresseDTO);
        Adresse adresse = adresseMapper.toEntity(adresseDTO);
        adresse = adresseRepository.save(adresse);
        return adresseMapper.toDto(adresse);
    }

    /**
     * Partially update a adresse.
     *
     * @param adresseDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AdresseDTO> partialUpdate(AdresseDTO adresseDTO) {
        LOG.debug("Request to partially update Adresse : {}", adresseDTO);

        return adresseRepository
            .findById(adresseDTO.getId())
            .map(existingAdresse -> {
                adresseMapper.partialUpdate(existingAdresse, adresseDTO);

                return existingAdresse;
            })
            .map(adresseRepository::save)
            .map(adresseMapper::toDto);
    }

    /**
     * Get all the adresses.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdresseDTO> findAll() {
        LOG.debug("Request to get all Adresses");
        return adresseRepository.findAll().stream().map(adresseMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the adresses where PmEntreprise is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdresseDTO> findAllWherePmEntrepriseIsNull() {
        LOG.debug("Request to get all adresses where PmEntreprise is null");
        return StreamSupport.stream(adresseRepository.findAll().spliterator(), false)
            .filter(adresse -> adresse.getPmEntreprise() == null)
            .map(adresseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the adresses where PmEtablissement is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdresseDTO> findAllWherePmEtablissementIsNull() {
        LOG.debug("Request to get all adresses where PmEtablissement is null");
        return StreamSupport.stream(adresseRepository.findAll().spliterator(), false)
            .filter(adresse -> adresse.getPmEtablissement() == null)
            .map(adresseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one adresse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdresseDTO> findOne(Long id) {
        LOG.debug("Request to get Adresse : {}", id);
        return adresseRepository.findById(id).map(adresseMapper::toDto);
    }

    /**
     * Delete the adresse by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Adresse : {}", id);
        adresseRepository.deleteById(id);
    }
}
