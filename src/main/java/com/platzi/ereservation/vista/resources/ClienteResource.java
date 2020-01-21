/**
 * 
 */
package com.platzi.ereservation.vista.resources;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platzi.ereservation.modelo.Cliente;
import com.platzi.ereservation.negocio.services.ClienteService;
import com.platzi.ereservation.vista.resources.vo.ClienteVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Clase que representa el servicio 
 * web del cliente
 * @author joanrincon
 *
 */
@RestController
@RequestMapping("/api/cliente")
@Api(tags="cliente")
public class ClienteResource {

	private final ClienteService clienteService;
	
	public ClienteResource(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	@PostMapping
	@ApiOperation(value="Crear Cliente", notes= "Servicio para crear un nuevo cliente")
	@ApiResponses(value= {@ApiResponse(code=201,message="Cliente creado correctamente"),
						@ApiResponse(code=400,message="Solicitud Invalida")
						} )
	public ResponseEntity<Cliente> createCliente(@RequestBody ClienteVO clienteVO){
		Cliente cliente = new Cliente();
		cliente.setApellidoCli(clienteVO.getApellidoCli());
		cliente.setDireccionCli(clienteVO.getDireccionCli());
		cliente.setEmailCli(clienteVO.getEmailCli());
		cliente.setNombreCli(clienteVO.getNombreCli());
		cliente.setTelefonoCli(cliente.getTelefonoCli());
		
		return new ResponseEntity<>(this.clienteService.create(cliente),HttpStatus.CREATED);
		
	}
	
	@PutMapping("/{identificacion}")
	@ApiOperation(value="Actualizar Cliente", notes= "Servicio para actualizar un cliente")
	@ApiResponses(value= {@ApiResponse(code=201,message="Cliente actualizado correctamente"),
						@ApiResponse(code=404,message="Cliente no encontrado")
						} )
	public ResponseEntity<Cliente> updateCliente(@PathVariable("identificacion") String identificacion, ClienteVO clienteVO ){
		Cliente cliente = this.clienteService.findByIdentificacion(identificacion);
		if( cliente == null ) {
			return new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND);
		}
		else {
			cliente.setApellidoCli(clienteVO.getApellidoCli());
			cliente.setDireccionCli(clienteVO.getDireccionCli());
			cliente.setEmailCli(clienteVO.getEmailCli());
			cliente.setIdentificacionCli(clienteVO.getIdentificacionCli());
			cliente.setNombreCli(clienteVO.getNombreCli());
			cliente.setTelefonoCli(cliente.getTelefonoCli());
			cliente.setIdCli(clienteVO.getIdCli());
		}
		return new ResponseEntity<>(this.clienteService.update(cliente),HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/{identificacion}")
	@ApiOperation(value="Eliminar Cliente", notes= "Servicio para eliminar un cliente")
	@ApiResponses(value= {@ApiResponse(code=201,message="Cliente eliminado correctamente"),
						@ApiResponse(code=404,message="cliente no encontrado")
						} )
	public void removeCliente(@PathVariable("identificacion") String identificacion) {
		Cliente cliente = this.clienteService.findByIdentificacion(identificacion);
		if( cliente != null ) {
			this.clienteService.delete(cliente);
		}
	}
	
	@GetMapping
	@ApiOperation(value="Encontrar Todos", notes= "Servicio para listar todos los clientes")
	@ApiResponses(value= {@ApiResponse(code=201,message="Clientes listados correctamente"),
						@ApiResponse(code=404,message="Clientes No encontrados")
						} )
	public ResponseEntity<List<Cliente>> findAll(){
		return ResponseEntity.ok(this.clienteService.findAll());
	}

}
