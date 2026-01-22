import java.util.List;
import java.util.Scanner;

public class Main {
    private static final RealEstateAgencyDAO agencyDao = new RealEstateAgencyDAO();
    private static final RealtorDAO realtorDao = new RealtorDAO();

    public static void main(String[] args) {
        System.out.println("Real Estate CLI. Type 'help' for commands.");
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            String cmd;
            String argsPart = "";
            int sp = line.indexOf(' ');
            if (sp >= 0) {
                cmd = line.substring(0, sp).trim();
                argsPart = line.substring(sp + 1).trim();
            } else {
                cmd = line;
            }

            try {
                switch (cmd) {
                    case "help":
                        printHelp();
                        break;

                    case "add-agency": {
                        String[] parts = argsPart.split("\\|", 2);
                        if (parts.length < 2) {
                            System.out.println("Usage: add-agency name|address");
                            break;
                        }
                        int n = agencyDao.insertAgency(parts[0].trim(), parts[1].trim());
                        System.out.println("Inserted rows: " + n);
                        break;
                    }

                    case "list-agencies": {
                        List<AgencyRecord> list = agencyDao.listAgencies();
                        if (list.isEmpty()) { System.out.println("(empty)"); }
                        for (AgencyRecord a : list) {
                            System.out.println(a.getId() + " | " + a.getName() + " | " + a.getAddress());
                        }
                        break;
                    }

                    case "update-agency": {
                        String[] parts = argsPart.split("\\s+", 2);
                        if (parts.length < 2) {
                            System.out.println("Usage: update-agency id name|address");
                            break;
                        }
                        int id = Integer.parseInt(parts[0]);
                        String[] nv = parts[1].split("\\|", 2);
                        if (nv.length < 2) {
                            System.out.println("Usage: update-agency id name|address");
                            break;
                        }
                        int n = agencyDao.updateAgency(id, nv[0].trim(), nv[1].trim());
                        System.out.println("Updated rows: " + n);
                        break;
                    }

                    case "delete-agency": {
                        if (argsPart.isEmpty()) {
                            System.out.println("Usage: delete-agency id");
                            break;
                        }
                        int id = Integer.parseInt(argsPart);
                        int n = agencyDao.deleteAgency(id);
                        System.out.println("Deleted rows: " + n);
                        break;
                    }

                    case "add-realtor": {
                        if (argsPart.isEmpty()) {
                            System.out.println("Usage: add-realtor name");
                            break;
                        }
                        int n = realtorDao.insertRealtor(argsPart);
                        System.out.println("Inserted rows: " + n);
                        break;
                    }

                    case "list-realtors": {
                        List<RealtorRecord> list = realtorDao.listRealtors();
                        if (list.isEmpty()) { System.out.println("(empty)"); }
                        for (RealtorRecord r : list) {
                            System.out.println(r.getId() + " | " + r.getName());
                        }
                        break;
                    }

                    case "update-realtor": {
                        String[] parts = argsPart.split("\\s+", 2);
                        if (parts.length < 2) {
                            System.out.println("Usage: update-realtor id name");
                            break;
                        }
                        int id = Integer.parseInt(parts[0]);
                        int n = realtorDao.updateRealtor(id, parts[1].trim());
                        System.out.println("Updated rows: " + n);
                        break;
                    }

                    case "delete-realtor": {
                        if (argsPart.isEmpty()) {
                            System.out.println("Usage: delete-realtor id");
                            break;
                        }
                        int id = Integer.parseInt(argsPart);
                        int n = realtorDao.deleteRealtor(id);
                        System.out.println("Deleted rows: " + n);
                        break;
                    }

                    case "exit":
                        System.out.println("Bye!");
                        return;

                    default:
                        System.out.println("Unknown command. Type 'help'.");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private static void printHelp() {
        System.out.println("Commands:");
        System.out.println("  help");
        System.out.println("  add-agency name|address");
        System.out.println("  list-agencies");
        System.out.println("  update-agency id name|address");
        System.out.println("  delete-agency id");
        System.out.println("  add-realtor name");
        System.out.println("  list-realtors");
        System.out.println("  update-realtor id name");
        System.out.println("  delete-realtor id");
        System.out.println("  exit");
    }
}