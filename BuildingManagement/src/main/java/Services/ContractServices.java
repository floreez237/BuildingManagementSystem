package Services;

import Model.*;
import Model.Rooms.Room;
import MenuUI.Tools;
import java.util.*;
import java.util.stream.Collectors;


public class ContractServices {
    Contract contract ; 

    public ContractServices(Contract contract) {
        this.contract = contract;
    }
    
public static List<Contract> searchContractInInterval(Room room, Date startDate, Date endDate, Level level) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date newStartDate = calendar.getTime();
        
        calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date newEndDate = calendar.getTime();
        
        List<Contract> contractList=new ArrayList<>(room.getListOfContract());
        sortContractListByDate(contractList);
        List<Contract> contractListInInterval = new ArrayList<>(contractList.stream().filter((contract) -> (contract.getDateOfCreation().after(newStartDate))).filter((contract) -> (contract.getDateOfCreation().before(newEndDate))).
                                                            collect(Collectors.toList()));
        
        
        return contractListInInterval;
    }

   public Date getExpirationDate() {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(contract.getDateOfPayment());
        calendar.add(Calendar.MONTH, contract.getDuration());
        Date expirationDate=calendar.getTime();
        return (Date) expirationDate.clone();
    }
   
    public void updateContract(int duration,Date dateOfPayment) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(dateOfPayment);
        contract.setDuration(duration);
        contract.setDateOfPayment(dateOfPayment);
        calendar.add(Calendar.MONTH, duration);
        getExpirationDate();      
    }

    public void displayAContract() {
        System.out.println("Contract Information");
        System.out.println("--------------------");
        System.out.println("Contract Id:" + contract.getContractId());
        System.out.println("Room Id:" + contract.getRoomId());
        System.out.print("\nDate of Creation:");
        Tools.displayDate(contract.getDateOfCreation());
        System.out.print("\nDate of Payment:");
        Tools.displayDate(contract.getDateOfPayment());
        System.out.println("\nRent duration:" + contract.getDuration()+" Months");
        System.out.print("Expiration Date:");
        Tools.displayDate(getExpirationDate());
        System.out.println("\nTenant Information\n------------------");
    }
 
    public static void sortContractListByDate(List<Contract> contractList) {
        contractList.sort(Comparator.comparing(Contract::getDateOfCreation));
    }
    
    public static Contract searchContractById(String contractId,List<Contract> listOfContracts){
        for(Contract contract:listOfContracts){
            if(contract.getContractId().equals(contractId)) {
                return contract;
            }
        }
        return null;
    }
   
    public boolean isContractValid() {
        return getExpirationDate().after(new Date()); 
    }
}
