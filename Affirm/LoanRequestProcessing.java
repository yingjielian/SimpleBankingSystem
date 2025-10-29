package Jack2025.Affirm;
import java.util.*;
public class LoanRequestProcessing {

    class Loan{
        String company;
        double amount;
        String id;
        public Loan(String id, String company, double amount)
        {
            this.id = id;
            this.company = company;
            this.amount = amount;
        }
    }

    private Map<String, String> childToParent = new HashMap<>();
    private Map<String, List<Loan>> loansByRootCompany = new HashMap<>();

    public LoanRequestProcessing(Map<String, List<String>> companyRelations)
    {
        // Construct chilt -> parent map
        for(Map.Entry<String, List<String>> entry : companyRelations.entrySet())
        {
            String parent = entry.getKey();
            for(String child : entry.getValue())
            {
                childToParent.put(child, parent);
            }
        }
    }

    private String findRoot(String company)
    {
        // find root until no parent company
        while(childToParent.containsKey(company))
        {
            company = childToParent.get(company);
        }
        return company;
    }

    public void addLoan(Loan loan)
    {
        String root = findRoot(loan.company);
        loansByRootCompany.computeIfAbsent(root, k -> new ArrayList<>()).add(loan);
    }

    public List<Loan> getLoansForCompany(String company)
    {
        String root = findRoot(company);
        return loansByRootCompany.getOrDefault(root, Collections.emptyList());
    }

    public Loan matchTransaction(String company, double amount)
    {
        String root = findRoot(company);
        List<Loan> loans = loansByRootCompany.get(root);
        if(loans == null) return null;

        for(Loan loan : loans)
        {
            if(loan.amount == amount)
            {
                return loan;
            }
        }

        return null;
    }
}
