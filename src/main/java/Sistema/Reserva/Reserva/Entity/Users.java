package Sistema.Reserva.Reserva.Entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "tb_users")
@Tag(name = "Usuários", description = "Tabelas dos Usuários")
public class Users implements UserDetails{
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true) @NotBlank
    private String name;
    @Column(unique = true) @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotNull @Enumerated(jakarta.persistence.EnumType.STRING)
    private RoleUser role;

    @OneToMany(mappedBy = "user")
    private List<AgendamentoRoomUser> agendamentosRoom;

    @OneToMany(mappedBy = "user")
    private List<AgendamentoStationUser> agendamentosStation;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + this.role));
        System.out.println("Authorities do Usuário: " + authorities);
        return authorities;
    }
    @Override
    public String getUsername() {
        return this.email;
    }
}
