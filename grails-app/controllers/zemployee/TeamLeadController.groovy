package zemployee

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import grails.converters.JSON
import java.io.File

class TeamLeadController {

    TeamLeadService teamLeadService
    UploadPointOfInterestFeaturedImageService uploadPointOfInterestFeaturedImageService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond teamLeadService.list(params), model:[teamLeadCount: teamLeadService.count()]
    }

    def show(Long id) {
        respond teamLeadService.get(id)
    }

    def create() {
        respond new TeamLead(params)
    }

    def save(TeamLead teamLead) {
        if (teamLead == null) {
            notFound()
            return
        }

        try {
            teamLeadService.save(teamLead)
        } catch (ValidationException e) {
            respond teamLead.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'teamLead.label', default: 'TeamLead'), teamLead.id])
                redirect teamLead
            }
            '*' { respond teamLead, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond teamLeadService.get(id)
    }

    def update(TeamLead teamLead) {
        if (teamLead == null) {
            notFound()
            return
        }

        try {
            teamLeadService.save(teamLead)
        } catch (ValidationException e) {
            respond teamLead.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'teamLead.label', default: 'TeamLead'), teamLead.id])
                redirect teamLead
            }
            '*'{ respond teamLead, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        teamLeadService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'teamLead.label', default: 'TeamLead'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'teamLead.label', default: 'TeamLead'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def editFeaturedImage(Long id) {
    TeamLead teamLead = teamLeadService.get(id)
    if (!teamLead) {
        notFound()
        return
    }
    [teamLead: teamLead]
}

def uploadFeaturedImage(FeaturedImageCommand cmd) {
    if (cmd.hasErrors()) {
        respond(cmd, model: [teamLead: cmd], view: 'editFeaturedImage')
        return
    }

    TeamLead teamLead = uploadPointOfInterestFeaturedImageService.uploadFeatureImage(cmd)
    if (teamLead == null) {
        notFound()
        return
    }

    if (teamLead.hasErrors()) {
        respond(teamLead, model: [teamLead: teamLead], view: 'editFeaturedImage')
        return
    }
    redirect teamLead
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
        def results =TeamLead.list()
        
        def teamLeadList = results.collect { teamlead ->
            [   
                id:teamlead.id,
                teamlead : teamlead.firstName,
                team: teamlead.team.name,
                designation: teamlead.designation,
                imageUrl: teamlead.featuredImageUrl,
                    // Include other team attributes here
        
                employee: [
                    teamlead.employee
                ]
            ]
         }

        render teamLeadList as JSON
    }
}
