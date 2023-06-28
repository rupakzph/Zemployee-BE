package zemployee

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class TeamLeadServiceSpec extends Specification {

    TeamLeadService teamLeadService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new TeamLead(...).save(flush: true, failOnError: true)
        //new TeamLead(...).save(flush: true, failOnError: true)
        //TeamLead teamLead = new TeamLead(...).save(flush: true, failOnError: true)
        //new TeamLead(...).save(flush: true, failOnError: true)
        //new TeamLead(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //teamLead.id
    }

    void "test get"() {
        setupData()

        expect:
        teamLeadService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<TeamLead> teamLeadList = teamLeadService.list(max: 2, offset: 2)

        then:
        teamLeadList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        teamLeadService.count() == 5
    }

    void "test delete"() {
        Long teamLeadId = setupData()

        expect:
        teamLeadService.count() == 5

        when:
        teamLeadService.delete(teamLeadId)
        sessionFactory.currentSession.flush()

        then:
        teamLeadService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        TeamLead teamLead = new TeamLead()
        teamLeadService.save(teamLead)

        then:
        teamLead.id != null
    }
}
