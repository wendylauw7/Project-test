package com.project.test.controller;

import com.project.test.model.dto.GlobalResponse;
import com.project.test.model.entity.OrganizationEntity;
import com.project.test.model.entity.UserEntity;
import com.project.test.repository.OrganizationRepository;
import com.project.test.repository.UserRepository;
import com.project.test.service.UserService;
import com.project.test.util.BaseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private UserRepository repo;

	@Autowired
	private OrganizationRepository repoOrg;

	@Autowired
	private BaseHelper helper;

	@GetMapping("/getAll")
	public ResponseEntity<?> findUser() throws Exception {
		return ResponseEntity.ok(repo.findAll());
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<?> findUserByUuid(@PathVariable int id) throws Exception {

		return ResponseEntity.ok(repo.findById(id));
	}

	@PutMapping(path = "/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String editUser(HttpServletRequest request, @RequestBody UserEntity param, @PathVariable int id)
			throws Exception {
		param.setUserId(id);
		repo.save(param);
		return ("berhasil");
	}

	@PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String saveUser(HttpServletRequest request, @RequestBody UserEntity param) throws Exception {
		UserEntity userEntity = repo.save(param);

		return userEntity.getPassword();
	}

	@GetMapping(path = "/changePassword/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponse> changePassword(HttpServletRequest request, @PathVariable String param)
			throws Exception {
		return service.changePassword(param, helper.getJwtFromRequest(request));
	}

	@GetMapping(path = "/getOrg", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getOrg(HttpServletRequest request, @RequestBody OrganizationEntity param)
			throws Exception {
		return ResponseEntity.ok(repoOrg.findAll());
	}

	@PostMapping(path = "/saveOrg", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String saveOrg(HttpServletRequest request, @RequestBody OrganizationEntity param) throws Exception {

		OrganizationEntity OrganizationEntity = repoOrg.save(param);
		return String.valueOf(OrganizationEntity.getOrgId());
	}

	@PutMapping(path = "/editOrg/{id}/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String editOrg(HttpServletRequest request, @RequestBody OrganizationEntity param, @PathVariable int id,
			@PathVariable int username) throws Exception {

		Optional<UserEntity> UserEntity = repo.findById(username);

		String a = "";
		String z = "superadmin";
		String x = UserEntity.get().getRoles();
		System.out.println(UserEntity.get().getRoles());
		System.out.println(x);
		System.out.println(z);
		if (x == z) {
			System.out.println("testif");
			param.setOrgId(id);
			repoOrg.save(param);
			a = "Berhasil melakukan Perubahan Data";
		} else {
			System.out.println("testelse");
			a = "Tidak Berhak Melakukan Perubahan";
		}

		return (a);

	}

	@PostMapping(path = "/deleteOrg/{id}/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String deleteOrg(HttpServletRequest request, @PathVariable int id, @PathVariable int username)
			throws Exception {
		Optional<UserEntity> UserEntity = repo.findById(username);

		String a = "";
		String z = "superadmin";
		String x = UserEntity.get().getRoles();
		System.out.println(UserEntity.get().getRoles());
		System.out.println(x);
		System.out.println(z);
		if (x == z) {
			System.out.println("testif");
			repoOrg.deleteById(id);
			a = "Berhasil Melakukan Penghapusan";
		} else {
			System.out.println("testelse");
			a = "Tidak Berhak Menghapus";
		}

		return (a);
	}

	@PostMapping(path = "/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String deleteUser(HttpServletRequest request, @PathVariable int id) throws Exception {
		repo.deleteById(id);
		return ("berhasil");
	}

}
