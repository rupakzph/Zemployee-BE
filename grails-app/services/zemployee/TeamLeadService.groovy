package zemployee

import grails.gorm.services.Service

@Service(TeamLead)
interface TeamLeadService {

    TeamLead get(Serializable id)

    List<TeamLead> list(Map args)

    Long count()

    void delete(Serializable id)

    TeamLead save(TeamLead teamLead)

    TeamLead updateFeaturedImageUrl(Serializable id, Long version, String featuredImageUrl)

}