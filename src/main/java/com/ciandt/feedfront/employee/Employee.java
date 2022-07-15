package com.ciandt.feedfront.employee;

import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class Employee implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String nome;
    private String sobrenome;
    private String email;
    private static String arquivoCriado = "employees.ser";
    private static File file = new File(arquivoCriado);
    private static ObjectOutputStream oos = null;
    private static ObjectInputStream ois = null;

//    String arquivoCriado = "arquivo.extensao"; //TODO: alterar de acordo com a sua implementação

    public Employee(String nome, String sobrenome, String email) throws ComprimentoInvalidoException {
        if(nome.length() <= 2 || sobrenome.length() <= 2){
            throw new ComprimentoInvalidoException("O nome e sobrenome devem ter mais do que 2 caracteres");

            this.id = UUID.randomUUID().toString();
            this.nome = nome;
            this.sobrenome = sobrenome;
            this.email = email;
        }
    }

    public static Employee salvarEmployee(Employee employee) throws ArquivoException, EmailInvalidoException {
        List<Employee> employeeList;

        employeeList = listarEmployees();
        employeeList.add(employee);
        saveFile(employeeList);

        return employee;
    }

    public static Employee saveFile(List<Employee> object) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        oos = new ObjectOutputStream(new BufferedOutputStream(fos));
        oos.writeObject(object);
        oos.flush();
        fos.flush();
        return null;
    }

    public static Employee atualizarEmployee(Employee employee) throws ArquivoException, EmailInvalidoException {
        return null;
    }



    public static List<Employee> listarEmployees() throws ArquivoException {
        ArrayList<Employee> employeeList = new ArrayList<>();
        try {

            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            employeeList = (ArrayList<Employee>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    public static Employee buscarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
        ArrayList<Employee> employeeList;

        Optional<Employee> employeeFound = null;
        try{
            ois = new ObjectInputStream(new FileInputStream(file));
            employeeList = (ArrayList<Employee>) ois.readObject();
            ois.close();

            employeeFound = Optional.ofNullable(employeeList.stream()
                    .filter(x -> id.equals(x.getId()))
                    .findAny()
                    .orElse(null));
            System.out.println(employeeFound);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return employeeFound.get();
    }

    public static void apagarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
        ArrayList<Employee> employeeList;
        try{
            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            employeeList = (ArrayList<Employee>) ois.readObject();
            ois.close();

            List<Employee> newEMployeList = employeeList.stream()
                    .filter(x -> !id.equals(x.getId()))
                    .collect(Collectors.toCollection(ArrayList::new));

            saveFile(newEMployeList);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public String getNome() {
        return null;
    }

    public void setNome(String nome) throws ComprimentoInvalidoException {
    }

    public String getSobrenome() {
        return null;
    }

    public void setSobrenome(String sobrenome) throws ComprimentoInvalidoException {
    }

    public String getEmail() {
        return null;
    }

    public void setEmail(String email) {
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", email='" + email + '\'' +
                ", arquivoCriado='" + arquivoCriado + '\'' +
                '}';
    }
}
