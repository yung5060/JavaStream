package com.example.streamapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class StreamApiTutorialApplication {

    static List<Employee> employees = new ArrayList<>();

    static {
        employees.add(
                new Employee("Yung", "Cho", 5000.0, List.of("Project1", "Project2"))
        );
        employees.add(
                new Employee("Minseok", "Kim", 6000.0, List.of("Project2", "Project3"))
        );
        employees.add(
                new Employee("YoungWoo", "Kim", 7000.0, List.of("Project3", "Project4"))
        );
        employees.add(
                new Employee("Seonil", "Kim", 8000.0, List.of("Project4", "Project5"))
        );

    }

    public static void main(String[] args) {
//		SpringApplication.run(StreamApiTutorialApplication.class, args);

        //foreach
        employees.stream().forEach(employee -> System.out.println(employee));

        //map
        //collect
        Set<Employee> increasedSalary =
                employees.stream().map(employee -> new Employee(
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getSalary() * 1.10,
                        employee.getProjects()
                )).collect(Collectors.toSet());
        System.out.println(increasedSalary);

		//filter
		//findFirst
		List<Employee> filterEmployees =  employees.stream().filter(employee -> employee.getSalary() > 6000.0).map(employee -> new Employee(
				employee.getFirstName(),
				employee.getLastName(),
				employee.getSalary() * 1.10,
				employee.getProjects()
		)).collect(Collectors.toList());
		System.out.println(filterEmployees);


		Employee firstEmployee =  employees.stream().filter(employee -> employee.getSalary() > 9000.0).map(employee -> new Employee(
				employee.getFirstName(),
				employee.getLastName(),
				employee.getSalary() * 1.10,
				employee.getProjects()
		)).findFirst()
				.orElse(null);
		System.out.println(firstEmployee);

		//flatMap
		String projects = employees.stream().map(employee -> employee.getProjects())
				.flatMap(strings -> strings.stream())
				.collect(Collectors.joining(", "));
		System.out.println(projects);

		//shortCircuit
		List<Employee> shortCircuit = employees.stream()
				.skip(1)
				.limit(1)
				.collect(Collectors.toList());
		System.out.println(shortCircuit);

		//Finite Data
		Stream.generate(Math::random)
				.limit(5)
				.forEach(value -> System.out.println(value));

		//sorting
		List<Employee> sortedEmployees = employees.stream().sorted((o1, o2) -> o1.getFirstName().compareToIgnoreCase(o2.getFirstName()))
				.collect(Collectors.toList());
		System.out.println(sortedEmployees);

		//min max
		Employee maxEmployee = employees.stream().max(Comparator.comparing(Employee::getSalary)).orElseThrow(NoSuchElementException::new);
		System.out.println(maxEmployee);
		Employee minEmployee = employees.stream().min(Comparator.comparing(Employee::getSalary)).orElseThrow(NoSuchElementException::new);
		System.out.println(minEmployee);

		//reduce
		Double totalSalary = employees.stream().map(employee -> employee.getSalary())
				.reduce(0.0, Double::sum);
		System.out.println(totalSalary);
    }
}
