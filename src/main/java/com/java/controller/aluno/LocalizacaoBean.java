package com.java.controller.aluno;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.map.MarkerDragEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.java.modelo.Aluno;
import com.java.service.AlunoService;
import com.java.util.Session;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class LocalizacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AlunoService alunoService;

	private List<Aluno> listarAlunos = new ArrayList<>();

	private MapModel draggableModel = new DefaultMapModel();

	private Marker marker;

	private float lat;

	private float logt;

	private float l1;

	private float l2;

	private String titulo;

	private String titulo2;

	private String id;

	private float latitude;

	private float longitude;

	private String foto;

	private String alunos;

	private String endereco;

	private Aluno aluno = new Aluno();

	@PostConstruct
	public void inicializar() {

		try {

			localizar();

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage(ex.getMessage());
		}

	}

	public void localizar() {

		Long idEmpresa = Session.retornaIdEmpresa();

		listarAlunos = alunoService.listarTodosPorEmpresa(idEmpresa);

		for (Aluno aluno : listarAlunos) {
			this.lat = aluno.getLatitude();
			this.logt = aluno.getLongitude();
			this.foto = aluno.getFoto();
			this.alunos = aluno.getNome();
			this.endereco = aluno.getEnderecoCompleto();

			double latDouble = this.lat;
			double longDouble = this.logt;

			LatLng coord1 = new LatLng(latDouble, longDouble);

			draggableModel.addOverlay(
					new Marker(coord1, alunos, foto, "../resources/img/ponto.png", alunos + " -> " + " " + endereco));

		}

	}

	public void onMarkerDrag(MarkerDragEvent event) throws SQLException {
		marker = event.getMarker();

		this.id = marker.getId();
		this.l1 = (float) marker.getLatlng().getLat();
		this.l2 = (float) marker.getLatlng().getLng();
		this.titulo = marker.getTitle();

		this.latitude = this.l1;

		this.longitude = this.l2;

		aluno.setLatitude(latitude);
		aluno.setLongitude(longitude);
		aluno.setNome(titulo);

	}

	public void onMarkerSelect(OverlaySelectEvent event) {
		marker = (Marker) event.getOverlay();
	}

	public List<Aluno> getListarAlunos() {
		return listarAlunos;
	}

	public void setListarAlunos(List<Aluno> listarAlunos) {
		this.listarAlunos = listarAlunos;
	}

	public MapModel getDraggableModel() {
		return draggableModel;
	}

	public void setDraggableModel(MapModel draggableModel) {
		this.draggableModel = draggableModel;
	}

	public Marker getMarker() {
		return marker;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTitulo2() {
		return titulo2;
	}

	public void setTitulo2(String titulo2) {
		this.titulo2 = titulo2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getAlunos() {
		return alunos;
	}

	public void setAlunos(String alunos) {
		this.alunos = alunos;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public float getL1() {
		return l1;
	}

	public void setL1(float l1) {
		this.l1 = l1;
	}

	public float getL2() {
		return l2;
	}

	public void setL2(float l2) {
		this.l2 = l2;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLogt() {
		return logt;
	}

	public void setLogt(float logt) {
		this.logt = logt;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

}
