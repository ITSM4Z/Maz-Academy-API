package com.maz.academy.user.admin;

import com.maz.academy.core.exceptions.UserNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {
    private final AdminRepository adminRepository;

    public AdminController(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }

    @PostMapping("/users/admins")
    public AdminResponseDTO create(@RequestBody AdminDTO user){
        Admin admin = adminRepository.save(toAdmin(user));
        return toAdminResponseDto(admin);
    }

    @GetMapping("/users/admins")
    public List<AdminResponseDTO> findAll(){
        return adminRepository.findAll()
                .stream()
                .map(this::toAdminResponseDto)
                .toList();
    }

    @GetMapping("/users/admins/{admin_id}")
    public AdminResponseDTO findById(@PathVariable int admin_id){
        return toAdminResponseDto(
                adminRepository.findById(admin_id)
                        .orElseThrow(() -> new UserNotFoundException("Admin not found!"))
        );
    }

    private Admin toAdmin(AdminDTO dto){
        Admin admin = new Admin();
        admin.setName(dto.name());
        admin.setEmail(dto.email());
        return admin;
    }

    private AdminResponseDTO toAdminResponseDto(Admin admin){
        return new AdminResponseDTO(
                admin.getName(),
                admin.getEmail()
        );
    }
}