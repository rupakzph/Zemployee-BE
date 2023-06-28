package zemployee

import grails.gorm.services.Service

@SuppressWarnings(['LineLength', 'UnusedVariable', 'SpaceAfterOpeningBrace', 'SpaceBeforeClosingBrace'])
@Service(Employee)
interface EmployeeService {

    Employee get(Serializable id)

    List<Employee> list(Map args)

    Long count()

    void delete(Serializable id)

    Employee save(Employee employee)

    Employee updateFeaturedImageUrl(Serializable id, Long version, String featuredImageUrl)
    

}