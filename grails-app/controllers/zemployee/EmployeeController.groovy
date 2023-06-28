package zemployee
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import grails.converters.JSON
import java.io.File
class EmployeeController {

    EmployeeService employeeService
    UploadPointOfInterestFeaturedImageService uploadPointOfInterestFeaturedImageService
     

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond employeeService.list(params), model:[employeeCount: employeeService.count(), employeeList :employeeService.list(params)]
    }

    def show(Long id) {
        respond employeeService.get(id)
    }

    def create() {
    def teamList = Team.list()
    def teamleadList = TeamLead.list()
    def departmentList = Department.list()
    respond new Employee(params), model: [teamList: teamList ,teamleadList : teamleadList, departmentList:departmentList]
    }

    def save(Employee employee) {
        if (employee == null) {
            notFound()
            return
        }
        try {
            employeeService.save(employee)
        } catch (ValidationException e) {
            respond employee.errors, view:'create'
            return
        }
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'employee.label', default: 'Employee'), employee.id])
                redirect employee
            }
            '*'{ respond employee, [status: OK] }
        }
    }

    def edit(Long id) {
        respond employeeService.get(id)
    }

    def update(Employee employee) {
        if (employee == null) {
            notFound()
            return
        }

        try {
            employeeService.save(employee)
        } catch (ValidationException e) {
            respond employee.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'employee.label', default: 'Employee'), employee.id])
                redirect employee
            }
            '*'{ respond employee, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        employeeService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'employee.label', default: 'Employee'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'employee.label', default: 'Employee'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def editFeaturedImage(Long id) {
    Employee employee = employeeService.get(id)
    if (!employee) {
        notFound()
        return
    }
    [employee: employee]
}

def uploadFeaturedImage(FeaturedImageCommand cmd) {
    if (cmd.hasErrors()) {
        respond(cmd, model: [employee: cmd], view: 'editFeaturedImage')
        return
    }

    Employee employee = uploadPointOfInterestFeaturedImageService.uploadFeatureImage(cmd)
    if (employee == null) {
        notFound()
        return
    }

    if (employee.hasErrors()) {
        respond(employee, model: [employee: employee], view: 'editFeaturedImage')
        return
    }
    redirect employee
}

def getContentType(imagePath) {
        // Extract the file extension from the image path
        def extension = imagePath.tokenize('/').last()?.tokenize('.').last()
        
        // Map common file extensions to content types
        switch (extension?.toLowerCase()) {
            case 'jpg':
            case 'jpeg':
                return 'image/jpeg'
            case 'png':
                return 'image/png'
            case 'gif':
                return 'image/gif'
            case 'svg':
                return 'image/svg+xml'
            // Add more cases for other file extensions if needed
            default:
                return 'application/octet-stream'
        }
    }

def viewImage() {
        // Retrieve the dynamic image file name from the request parameters
        def fileName = params.fileName
        
        // Ensure the file name is not null or empty
        if (fileName) {
            // Construct the image path based on the file name
            def imagePath = "/Users/rupakshrestha/Desktop/temp/${fileName}"
            
        if (new File(imagePath).exists()) {
            // Set the response content type based on the image file extension
            response.setContentType(getContentType(imagePath))
            
            // Send the image file as response output
            response.outputStream << new File(imagePath).newInputStream()
            
            // Ensure the response is properly closed
            response.outputStream.flush()
            response.outputStream.close()
        } else {
            // Handle the case where the image file is not found
            render(status: 404, text: "Image not found")
        }
    }
    }




def ListAll(){
        def results =Employee.list()
        
        def employeeList = results.collect { employee ->
            [
                id: employee.id,
                firstName:employee.firstName,
                lastName:employee.lastName,
                gender: employee.gender,
                designation:employee.designation,
                email:employee.email,
                phoneNumber: employee.phoneNumber,
                address: employee.address,
                isTeamlead: employee.isTeamlead,
                nationality: employee.nationality,
                imageUrl: employee.featuredImageUrl,
                department:employee.department.name,

                subordinate: employee.hasProperty('employee') ? employee.employee : [],
                team: [
                  name:employee.team.name
                    // Include other team attributes here
                ],
                teamLead : [
                name : employee.teamLead
                ],

            ]
         }

        render employeeList as JSON
    }


}
