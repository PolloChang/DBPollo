package dbpollo

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: "system",action: "index")
//        "/" view: "/system/index"
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
