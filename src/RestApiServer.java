import io.javalin.Javalin;
import io.javalin.http.Context;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class RestApiServer {
    
    private static final Logger logger = LoggerFactory.getLogger(RestApiServer.class);
    private static final Gson gson = new Gson();
    private static final RealEstateAgencyDAO agencyDAO = new RealEstateAgencyDAO();
    private static final RealtorDAO realtorDAO = new RealtorDAO();

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
                List<AgencyRecord> agencies = agencyDAO.listAgencies();
                ctx.json(agencies);
            } catch (Exception e) {
                handleError(ctx, e);
            }
        });

        app.get("/api/agencies/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                AgencyRecord agency = agencyDAO.getAgencyById(id);
                if (agency != null) {
                    ctx.json(agency);
                } else {
                    ctx.status(404).json(Map.of("success", false, "error", "Agency not found"));
                }
            } catch (NumberFormatException e) {
                ctx.status(400).json(Map.of("success", false, "error", "Invalid ID format"));
            } catch (Exception e) {
                handleError(ctx, e);
            }
        });

        app.post("/api/agencies", ctx -> {
            try {
                AgencyRecord agency = gson.fromJson(ctx.body(), AgencyRecord.class);
                
                if (agency.getName() == null || agency.getName().trim().isEmpty()) {
                    ctx.status(400).json(Map.of("success", false, "error", "Name is required"));
                    return;
                }
                if (agency.getAddress() == null || agency.getAddress().trim().isEmpty()) {
                    ctx.status(400).json(Map.of("success", false, "error", "Address is required"));
                    return;
                }
                
                int result = agencyDAO.insertAgency(agency.getName(), agency.getAddress());
                if (result > 0) {
                    ctx.status(201).json(Map.of("success", true, "message", "Agency created successfully"));
                } else {
                    ctx.status(500).json(Map.of("success", false, "error", "Failed to create agency"));
                }
            } catch (Exception e) {
                handleError(ctx, e);
            }
        });

        app.put("/api/agencies/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                AgencyRecord agency = gson.fromJson(ctx.body(), AgencyRecord.class);
                
                if (agency.getName() == null || agency.getName().trim().isEmpty()) {
                    ctx.status(400).json(Map.of("success", false, "error", "Name is required"));
                    return;
                }
                if (agency.getAddress() == null || agency.getAddress().trim().isEmpty()) {
                    ctx.status(400).json(Map.of("success", false, "error", "Address is required"));
                    return;
                }
                
                int result = agencyDAO.updateAgency(id, agency.getName(), agency.getAddress());
                if (result > 0) {
                    ctx.json(Map.of("success", true, "message", "Agency updated successfully"));
                } else {
                    ctx.status(404).json(Map.of("success", false, "error", "Agency not found"));
                }
            } catch (NumberFormatException e) {
                ctx.status(400).json(Map.of("success", false, "error", "Invalid ID format"));
            } catch (Exception e) {
                handleError(ctx, e);
            }
        });

        app.delete("/api/agencies/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                int result = agencyDAO.deleteAgency(id);
                if (result > 0) {
                    ctx.json(Map.of("success", true, "message", "Agency deleted successfully"));
                } else {
                    ctx.status(404).json(Map.of("success", false, "error", "Agency not found"));
                }
            } catch (NumberFormatException e) {
                ctx.status(400).json(Map.of("success", false, "error", "Invalid ID format"));
            } catch (Exception e) {
                handleError(ctx, e);
            }
        });

        // Realtor Endpoints
        app.get("/api/realtors", ctx -> {
            try {
                List<RealtorRecord> realtors = realtorDAO.listRealtors();
                ctx.json(realtors);
            } catch (Exception e) {
                handleError(ctx, e);
            }
        });

        app.get("/api/realtors/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                RealtorRecord realtor = realtorDAO.getRealtorById(id);
                if (realtor != null) {
                    ctx.json(realtor);
                } else {
                    ctx.status(404).json(Map.of("success", false, "error", "Realtor not found"));
                }
            } catch (NumberFormatException e) {
                ctx.status(400).json(Map.of("success", false, "error", "Invalid ID format"));
            } catch (Exception e) {
                handleError(ctx, e);
            }
        });

        app.post("/api/realtors", ctx -> {
            try {
                RealtorRecord realtor = gson.fromJson(ctx.body(), RealtorRecord.class);
                
                if (realtor.getName() == null || realtor.getName().trim().isEmpty()) {
                    ctx.status(400).json(Map.of("success", false, "error", "Name is required"));
                    return;
                }
                
                int result = realtorDAO.insertRealtor(realtor.getName());
                if (result > 0) {
                    ctx.status(201).json(Map.of("success", true, "message", "Realtor created successfully"));
                } else {
                    ctx.status(500).json(Map.of("success", false, "error", "Failed to create realtor"));
                }
            } catch (Exception e) {
                handleError(ctx, e);
            }
        });

        app.put("/api/realtors/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                RealtorRecord realtor = gson.fromJson(ctx.body(), RealtorRecord.class);
                
                if (realtor.getName() == null || realtor.getName().trim().isEmpty()) {
                    ctx.status(400).json(Map.of("success", false, "error", "Name is required"));
                    return;
                }
                
                int result = realtorDAO.updateRealtor(id, realtor.getName());
                if (result > 0) {
                    ctx.json(Map.of("success", true, "message", "Realtor updated successfully"));
                } else {
                    ctx.status(404).json(Map.of("success", false, "error", "Realtor not found"));
                }
            } catch (NumberFormatException e) {
                ctx.status(400).json(Map.of("success", false, "error", "Invalid ID format"));
            } catch (Exception e) {
                handleError(ctx, e);
            }
        });

        app.delete("/api/realtors/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                int result = realtorDAO.deleteRealtor(id);
                if (result > 0) {
                    ctx.json(Map.of("success", true, "message", "Realtor deleted successfully"));
                } else {
                    ctx.status(404).json(Map.of("success", false, "error", "Realtor not found"));
                }
            } catch (NumberFormatException e) {
                ctx.status(400).json(Map.of("success", false, "error", "Invalid ID format"));
            } catch (Exception e) {
                handleError(ctx, e);
            }
        });

        System.out.println("REST API Server started on http://localhost:7070");
    }

    private static void handleError(Context ctx, Exception e) {
        logger.error("Error processing request: {}", e.getMessage(), e);
        ctx.status(500).json(Map.of("success", false, "error", "Internal server error: " + e.getMessage()));
    }
}
