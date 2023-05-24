package com.traum.plugins

import com.traum.routes.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        auth()

        authenticate {
            orders()
            elementOfOrders()
            logs()
            categories()
            bookings()
            employees()
            items()
            measuresOfItem()
            measurements()
            pricesOfItem()
            tables()
        }

        val routes = getAllRoutes()
        get("/") {
            var str = "all routes: \n"
            routes.forEach { str += "- [ ] $it\n" }
            call.respond(str)
        }
    }
}