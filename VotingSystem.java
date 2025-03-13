package bha;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Voter{
	private String name;
	private int age;
	private String voterId;
	Gender gender;
	CandidateVote candidateVote;
	Rolese rolese;
	public String getFormattedDetails() {
        return String.format("| %-20s | %-5d | %-15s | %-10s | %-15s | %-10s |", 
                name, age, voterId, gender, candidateVote, rolese);
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getVoterId() {
		return voterId;
	}
	public void setVoterId(String voterId) {
		this.voterId = voterId;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public CandidateVote getCandidateVote() {
		return candidateVote;
	}
	public void setCandidateVote(CandidateVote candidateVote) {
		this.candidateVote = candidateVote;
	}
	public Rolese getRolese() {
		return rolese;
	}
	public void setRolese(Rolese rolese) {
		this.rolese = rolese;
	}
	public Voter(String name, int age, String voterId, Gender gender, CandidateVote candidateVote, Rolese rolese) {
		
		this.name = name;
		this.age = age;
		this.voterId = voterId;
		this.gender = gender;
		this.candidateVote = candidateVote;
		this.rolese = rolese;
	}
	
	
}
class Elec {
    Voter voter;
    Elec next;

    Elec(Voter voter) {
        this.voter = voter;
        this.next = null;
    }
}

// Election class (Handles Voting System)
class Election {
    Elec head = null;

    // Add a voter
    void add(Voter voter) {
        try {
            if (voter == null) {
                throw new Exception("Invalid voter data.");
            }
            Elec newnode = new Elec(voter);
            
            if (head == null) {
                head = newnode;
            } else {
                Elec curr = head;
                while (curr.next != null) {
                    curr = curr.next;
                }
                curr.next = newnode;
            }
            System.out.println("Voter added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding voter: " + e.getMessage());
        }
    }

    // Get all voters (Only Admin can view)
    void getAllVoters(Rolese role) {
        try {
            if (role != Rolese.Admining) {
                throw new Exception("Access Denied! Only Admin can view all voters.");
            }

            if (head == null) {
                System.out.println("No voters found.");
                return;
            }

            System.out.println("+----------------------+-------+-----------------+------------+-----------------+------------+");
            System.out.println("| Name                 | Age   | Voter ID        | Gender     | Candidate Vote  | Role       |");
            System.out.println("+----------------------+-------+-----------------+------------+-----------------+------------+");

            Elec temp = head;
            while (temp != null) {
                System.out.println(temp.voter.getFormattedDetails());
                temp = temp.next;
            }

            System.out.println("+----------------------+-------+-----------------+------------+-----------------+------------+");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    void saveToFile(String filename) {
        try (FileWriter w = new FileWriter(filename)) {
        	 w.write("+----------------------+-------+-----------------+------------+-----------------+------------+\n");
        	 w.write("| Name                 | Age   | Voter ID        | Gender     | Candidate Vote  | Role       |\n");
        	 w.write("+----------------------+-------+-----------------+------------+-----------------+------------+\n");

            Elec curr = head;
            while (curr != null) {
                w.write(curr.voter.getFormattedDetails() + "\n");
                curr = curr.next;
            }

       	 w.write("+----------------------+-------+-----------------+------------+-----------------+------------+\n");

            System.out.println("Voted data saved successfully in table format.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    // Find voter by ID
    void getVoterById(String voterId) {
        try {
            Elec temp = head;
            while (temp != null) {
                if (temp.voter.getVoterId().equalsIgnoreCase(voterId)) {
                    System.out.println("Voter Found:");
                    System.out.println(temp.voter.getFormattedDetails());
                    return;
                }
                temp = temp.next;
            }
            throw new Exception("Voter with ID " + voterId + " not found.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Delete voter by ID
    void deleteVoter(String voterId) {
        try {
            if (head == null) {
                throw new Exception("No voters registered yet.");
            }

            if (head.voter.getVoterId().equalsIgnoreCase(voterId)) {
                head = head.next;
                System.out.println("Voter deleted successfully.");
                return;
            }

            Elec temp = head;
            while (temp.next != null) {
                if (temp.next.voter.getVoterId().equalsIgnoreCase(voterId)) {
                    temp.next = temp.next.next;
                    System.out.println("Voter deleted successfully.");
                    return;
                }
                temp = temp.next;
            }

            throw new Exception("Voter with ID " + voterId + " not found.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

// Main class
public class VotingSystem {
    public static void main(String[] args) {
        Election election = new Election();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nVoting System Menu:");
            System.out.println("1 - Register a Voter");
            System.out.println("2 - View All Voters (Admin Only)");
            System.out.println("3 - Find Voter by ID");
            System.out.println("4 - Delete Voter by ID");
            System.out.println("5 - Saved date in table");
            System.out.println("6 - Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();
                        sc.nextLine(); // Consume newline
                        if (age < 18) {
                            throw new Exception("You must be at least 18 to register as a voter.");
                        }

                        System.out.print("Enter Voter ID: ");
                        String voterId = sc.nextLine();

                        System.out.print("Enter Gender (MALE/FEMALE/OTHER): ");
                        Gender gender = Gender.valueOf(sc.nextLine());

                        System.out.print("Enter Candidate (CANDIDATE_A/CANDIDATE_B/CANDIDATE_C): ");
                        CandidateVote candidateVote = CandidateVote.valueOf(sc.nextLine());

                        System.out.print("Enter Role (ADMIN/VOTER): ");
                        Rolese role = Rolese.valueOf(sc.nextLine());

                        Voter voter = new Voter(name, age, voterId, gender, candidateVote,role);
                        election.add(voter);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    election.getAllVoters(Rolese.Admining);
                    break;

                case 3:
                    System.out.print("Enter Voter ID to Search: ");
                    election.getVoterById(sc.nextLine());
                    break;

                case 4:
                    System.out.print("Enter Voter ID to Delete: ");
                    election.deleteVoter(sc.nextLine());
                    break;
                case 5:
                	System.out.print("Entered the data");
                	election.saveToFile("voti.txt");
                	break;

                case 6:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

}
