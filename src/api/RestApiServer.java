package api;

import com.google.gson.Gson;
import dto.AgencyRecord;
import dto.PropertyRecord;
import dto.RealtorRecord;
import exceptions.DataAccessException;
import exceptions.InvalidInputException;
import exceptions.NotFoundException;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.jdbc.PropertyDAO;
import repository.jdbc.RealEstateAgencyDAO;
import repository.jdbc.RealtorDAO;
import service.AgencyService;
import service.DefaultAgencyService;
import service.DefaultPropertyService;
import service.DefaultRealtorService;
import service.PropertyService;
import service.RealtorService;

import java.util.List;
import java.util.Map;

public class RestApiServer {
    
    private static final Logger logger = LoggerFactory.getLogger(RestApiServer.class);
    private static final Gson gson = new Gson();
    private static final AgencyService agencyService = new DefaultAgencyService(new RealEstateAgencyDAO());
    private static final RealtorService realtorService = new DefaultRealtorService(new RealtorDAO());
    private static final PropertyService propertyService = new DefaultPropertyService(new PropertyDAO());

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            // Note: CORS is configured for development/testing purposes
            // In production, restrict to specific trusted origins
            config.plugins.enableCors(cors -> {
                cors.add(it -> {
                    it.anyHost();
                });
            });
        }).start(7070);

        // Agency Endpoints
        app.get("/api/agencies", ctx -> {
            try {
                List<AgencyRecord> agencies = agencyService.listAgencies();
                ctx.json(agencies);
            } catch (Exception e) {
                handleError(ctx, e);
            }
        });

        app.get("/api/agencies/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                AgencyRecord agency = agencyService.getAgency(id);
                ctx.json(agency);
            } catch (NumberFormatException e) {
                ctx.status(400).json(Map.of("success", false, "error", "Invalid ID format"));
            } catch (RuntimeException e) {
                handleError(ctx, e);
            }
        });

        app.post("/api/agencies", ctx -> {
            try {
                AgencyRecord agency = gson.fromJson(ctx.body(), AgencyRecord.class);
                agencyService.createAgency(agency);
                ctx.status(201).json(Map.of("success", true, "message", "Agency created successfully"));
            } catch (RuntimeException e) {
                handleError(ctx, e);
            }
        });

        app.put("/api/agencies/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                AgencyRecord agency = gson.fromJson(ctx.body(), AgencyRecord.class);

                agencyService.updateAgency(id, agency);
                ctx.json(Map.of("success", true, "message", "Agency updated successfully"));
            } catch (NumberFormatException e) {
                ctx.status(400).json(Map.of("success", false, "error", "Invalid ID format"));
            } catch (RuntimeException e) {
                handleError(ctx, e);
            }
        });

        app.delete("/api/agencies/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                agencyService.deleteAgency(id);
                ctx.json(Map.of("success", true, "message", "Agency deleted successfully"));
            } catch (NumberFormatException e) {
                ctx.status(400).json(Map.of("success", false, "error", "Invalid ID format"));
            } catch (RuntimeException e) {
                handleError(ctx, e);
            }
        });

        // Realtor Endpoints
        app.get("/api/realtors", ctx -> {
            try {
                List<RealtorRecord> realtors = realtorService.listRealtors();
                ctx.json(realtors);
            } catch (RuntimeException e) {
                handleError(ctx, e);
            }
        });

        app.get("/api/realtors/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                RealtorRecord realtor = realtorService.getRealtor(id);
                ctx.json(realtor);
            } catch (NumberFormatException e) {
                ctx.status(400).json(Map.of("success", false, "error", "Invalid ID format"));
            } catch (RuntimeException e) {
                handleError(ctx, e);
            }
        });

        app.post("/api/realtors", ctx -> {
            try {
                RealtorRecord realtor = gson.fromJson(ctx.body(), RealtorRecord.class);
                realtorService.createRealtor(realtor);
                ctx.status(201).json(Map.of("success", true, "message", "Realtor created successfully"));
            } catch (RuntimeException e) {
                handleError(ctx, e);
            }
        });

        app.put("/api/realtors/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                RealtorRecord realtor = gson.fromJson(ctx.body(), RealtorRecord.class);
                realtorService.updateRealtor(id, realtor);
                ctx.json(Map.of("success", true, "message", "Realtor updated successfully"));
            } catch (NumberFormatException e) {
                ctx.status(400).json(Map.of("success", false, "error", "Invalid ID format"));
            } catch (RuntimeException e) {
                handleError(ctx, e);
            }
        });

        app.delete("/api/realtors/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                realtorService.deleteRealtor(id);
                ctx.json(Map.of("success", true, "message", "Realtor deleted successfully"));
            } catch (NumberFormatException e) {
                ctx.status(400).json(Map.of("success", false, "error", "Invalid ID format"));
            } catch (RuntimeException e) {
                handleError(ctx, e);
            }
        });

        // Property Endpoints
        app.get("/api/properties", ctx -> {
            try {
                List<PropertyRecord> properties = propertyService.listProperties();
                ctx.json(properties);
            } catch (RuntimeException e) {
                handleError(ctx, e);
            }
        });

        app.get("/api/properties/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                PropertyRecord property = propertyService.getProperty(id);
                ctx.json(property);
            } catch (NumberFormatException e) {
                ctx.status(400).json(Map.of("success", false, "error", "Invalid ID format"));
            } catch (RuntimeException e) {
                handleError(ctx, e);
            }
        });

        app.post("/api/properties", ctx -> {
            try {
                PropertyRecord property = gson.fromJson(ctx.body(), PropertyRecord.class);
                propertyService.createProperty(property);
                ctx.status(201).json(Map.of("success", true, "message", "Property created successfully"));
            } catch (RuntimeException e) {
                handleError(ctx, e);
            }
        });

        app.put("/api/properties/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                PropertyRecord property = gson.fromJson(ctx.body(), PropertyRecord.class);
                propertyService.updateProperty(id, property);
                ctx.json(Map.of("success", true, "message", "Property updated successfully"));
            } catch (NumberFormatException e) {
                ctx.status(400).json(Map.of("success", false, "error", "Invalid ID format"));
            } catch (RuntimeException e) {
                handleError(ctx, e);
            }
        });

        app.delete("/api/properties/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                propertyService.deleteProperty(id);
                ctx.json(Map.of("success", true, "message", "Property deleted successfully"));
            } catch (NumberFormatException e) {
                ctx.status(400).json(Map.of("success", false, "error", "Invalid ID format"));
            } catch (RuntimeException e) {
                handleError(ctx, e);
            }
        });

        System.out.println("REST API Server started on http://localhost:7070");
    }

    private static void handleError(Context ctx, RuntimeException e) {
        if (e instanceof InvalidInputException) {
            ctx.status(400).json(Map.of("success", false, "error", e.getMessage()));
            return;
        }
        if (e instanceof NotFoundException) {
            ctx.status(404).json(Map.of("success", false, "error", e.getMessage()));
            return;
        }
        if (e instanceof DataAccessException) {
            logger.error("Database error: {}", e.getMessage(), e);
            ctx.status(500).json(Map.of("success", false, "error", "Database error: " + e.getMessage()));
            return;
        }
        logger.error("Error processing request: {}", e.getMessage(), e);
        ctx.status(500).json(Map.of("success", false, "error", "Internal server error: " + e.getMessage()));
    }
}
