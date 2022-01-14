package it.lvsemergency.accountManagement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> user = userRepository.findByUsername(username);
		
		user.orElseThrow(() -> new UsernameNotFoundException(username + " not found."));

		return user.map(UserDetailsImpl::new).get();        
	}
	
	public UserDTO userInformationResponse(UserDetailsImpl userInfo) {
		Optional<User> user = userRepository.findByUsername(userInfo.getUsername());
		
		user.orElseThrow(() -> new UsernameNotFoundException(userInfo.getUsername() + " not found."));

		return user.map(UserDTO::new).get();
	}
	
	public User modify(UserDTO userDto) {
		
		Optional<User> user = userRepository.findByUsername(userDto.getUsername());
		
		if (user.get() == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		
		User userToModify = user.get();
		
		if (!userDto.getName().isEmpty()) userToModify.setName(userDto.getName());;
		
		// Bisogna continuare così per tutti gli altri.
		// L'alternativa è che nel DTO tutti i campi sono richiesti, ma questo farebbe perdere il significato del DTO
		// Cercare di capire come è meglio farlo.
		
		return userRepository.save(userToModify);
	}
	
	public List<User> getUsers() {
		return userRepository.findAll(Sort.by(Sort.Direction.ASC, "idUser"));
	}
	
	public User getUser(Integer idUser) {
		Optional<User> user = userRepository.findById(idUser);

		if (!user.isPresent())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist");

		return user.get();
	}
	
	public void deleteUser(Integer idUser) {
		Optional<User> userToDelete = userRepository.findById(idUser);

		if (!userToDelete.isPresent())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user to delete");

		userRepository.delete(userToDelete.get());
	}


	

}
