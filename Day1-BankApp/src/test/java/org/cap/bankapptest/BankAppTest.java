package org.cap.bankapptest;

import static org.junit.Assert.*;

import org.cap.dao.AccountDao;
import org.cap.dto.Account;
import org.cap.dto.Address;
import org.cap.dto.Customer;
import org.cap.exception.InsufficientBalanceException;
import org.cap.exception.InvalidInitialAmountException;
import org.cap.service.AcccountService;
import org.cap.service.AccountServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;



public class BankAppTest {
	
	//proxy object
	@Mock
	private AccountDao accountDao;
	
	//private AcccountService accountService=new AccountServiceImpl();
	private AcccountService accountService;
	
	@Before
	public void setUp(){
		//All proxy will be intialized
		MockitoAnnotations.initMocks(this);
		accountService=new AccountServiceImpl(accountDao);
	}
	
	
	@Test
	public void test_addNumber_Method() {
		//fail("Not yet implemented");
		assertEquals(60, accountService.addNumbers(10, 50));
	}

	@Test(expected=IllegalArgumentException.class)
	public void test_addAccount_with_null_customer() throws InvalidInitialAmountException{
		
		Customer customer=null;
		accountService.addAccount(customer, 1300);
	}
	@Test(expected=InvalidInitialAmountException.class)
	public void test_addAccount_with_insufficient_balance() throws InvalidInitialAmountException{
		Customer customer=new Customer();
		customer.setCustName("Revathi");
		Address address=new Address();
		address.setAddressLine("Whitefield");
		customer.setCustAddress(address);
		accountService.addAccount(customer, 200);
	}
	
	@Test
	public void addAccount_with_mockito_Dao() throws InvalidInitialAmountException{
		Customer customer=new Customer();
		customer.setCustName("Revathi");
		Address address=new Address();
		address.setAddressLine("Whitefield");
		customer.setCustAddress(address);
		
		Account account=new Account();
		account.setCustomer(customer);
		account.setAmount(3000);
		//account.setAccountNo(accountNo);
		
		//dummy declaration with Mockito
				Mockito.when(accountDao.createAccount(account)).thenReturn(true);
				
				//Actual Logic exist in Service Tier
				Account newAccount=accountService.addAccount(customer, 3000);
				
				//Mockito verify
				Mockito.verify(accountDao).createAccount(account);
				
				//Manual Verification
				assertEquals(newAccount.getAmount(), account.getAmount(),0.0);
	}
	
	@Test
	public void test_findAccountById(){
		Customer customer=new Customer();
		customer.setCustName("Revathi");
		Address address=new Address();
		address.setAddressLine("Whitefield");
		customer.setCustAddress(address);
		
		Account account=new Account();
		account.setCustomer(customer);
		account.setAmount(3000);
		account.setAccountNo(1234);
		
		//Mockito declaration
		Mockito.when(accountDao.findAccountById(1234)).thenReturn(account);
		
		//Actual call
		Account foundAcc=accountService.findAccountById(1234);
		
		//Mockito verification
		Mockito.verify(accountDao).findAccountById(1234);
		
		assertEquals(account.getAccountNo(), foundAcc.getAccountNo());
	}
	
	@Test
	public void test_withdrawl() throws InsufficientBalanceException{
		Customer customer=new Customer();
		customer.setCustName("Revathi");
		Address address=new Address();
		address.setAddressLine("Whitefield");
		customer.setCustAddress(address);
		
		Account account=new Account();
		account.setCustomer(customer);
		account.setAmount(3000);
		account.setAccountNo(1234);
		
		//Mockito declaration
		Mockito.when(accountDao.findAccountById(1234)).thenReturn(account);
		
		Account account2=accountService.withdraw(1234, 1000);
		
		Mockito.verify(accountDao).findAccountById(1234);
		
		assertEquals(2000, account2.getAmount(), 0.0);
	}
	
	@Test
	public void test_deposit(){
		Customer customer=new Customer();
		customer.setCustName("Revathi");
		Address address=new Address();
		address.setAddressLine("Whitefield");
		customer.setCustAddress(address);
		
		Account account=new Account();
		account.setCustomer(customer);
		account.setAmount(3000);
		account.setAccountNo(1234);
		
		Mockito.when(accountDao.findAccountById(1234)).thenReturn(account);
		
		Account account3=accountService.deposit(1234, 2000);
		
		Mockito.verify(accountDao).findAccountById(1234);
		
		assertEquals(5000, account3.getAmount(), 0.0);
	}
	
	

}
