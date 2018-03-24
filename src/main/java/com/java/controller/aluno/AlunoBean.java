package com.java.controller.aluno;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.java.modelo.Aluno;
import com.java.modelo.EstadoCivil;
import com.java.modelo.Servico;
import com.java.modelo.Sexo;
import com.java.relatorio.RelatorioService;
import com.java.service.AlunoService;
import com.java.service.EmpresaService;
import com.java.service.EstadoCivilService;
import com.java.service.ServicoService;
import com.java.service.SexoService;
import com.java.service.UsuarioService;
import com.java.util.FileUploadCopiarArquivo;
import com.java.util.NegocioException;
import com.java.util.Session;
import com.java.util.WebServiceCep;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class AlunoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AlunoService alunoService;

	@Inject
	private RelatorioService relatorioService;

	@Inject
	private EmpresaService empresaService;

	@Inject
	private ServicoService servicoService;

	@Inject
	private UsuarioService usuarioService;

	private List<Aluno> listarAlunos;

	@Inject
	private EstadoCivilService estdocivilService;

	@Inject
	private SexoService sexoService;

	private Map<String, EstadoCivil> listarEstadoCivis = new HashMap<String, EstadoCivil>();

	private Map<String, Servico> listaServicos = new HashMap<String, Servico>();

	private Map<String, Sexo> listarSexos = new HashMap<String, Sexo>();

	private UploadedFile arquivoUP;

	private MapModel geoModel = new DefaultMapModel();

	private String centerGeoMap = "-2.5357198, -44.21076360000001";

	private String enderecoCompleto;

	private MapModel advancedModel;

	private String num;

	private String cep;

	private float lt1;

	private float lt2;

	private double latitude;

	private double longitude;

	private Aluno alunoEdicao = new Aluno();

	private Aluno alunoSelecionada;

	private Aluno aluno;

	@PostConstruct
	public void init() throws SQLException {
		consultar();
		preencheListasSelect();
		limpar();

	}

	public void consultar() throws SQLException {

		Long idEmpresa = Session.retornaIdEmpresa();
		Long idUsuario = Session.retornaIdUsuarioLogado();

		listarAlunos = alunoService.listarTodosPorEmpresaUsuario(idEmpresa, idUsuario);

	}

	public void prepararNovoCadastro() {
		alunoEdicao = new Aluno();
	}

	public void salvar() throws SQLException {
		try {

			Long idUser = Session.retornaIdUsuarioLogado();
			Long idEmpresa = Session.retornaIdEmpresa();

			this.alunoEdicao.setUsuario(usuarioService.retornarUsuarioPorID(String.valueOf(new Long(idUser))));
			this.alunoEdicao.setEmpresa(empresaService.retornarEmpresaPorID(idEmpresa));

			String nomeArquivo = "";
			if (arquivoUP != null) {
				FileUploadCopiarArquivo.iniciarCopia(arquivoUP);
				nomeArquivo = arquivoUP.getFileName();
				alunoEdicao.setFoto(nomeArquivo);
			} else {
				alunoEdicao.setFoto("");
			}

			this.alunoService.salvar(alunoEdicao);

			FacesUtil.addSuccessMessage("Arquivo Salvo com Sucesso !!!!! ");

			RequestContext.getCurrentInstance()
					.update(Arrays.asList("frm:msgs", "frm:alunoTable", ":frmLocal:dialogLocal", "frmLocal:mapa"));

			consultar();
			preencheListasSelect();
			limpar();

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}
	}

	public void excluir() {
		try {
			alunoService.excluir(alunoSelecionada);
			this.listarAlunos.remove(alunoSelecionada);
			FacesUtil.addSuccessMessage("Aluno " + alunoSelecionada.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	private void preencheListasSelect() {

		listarSexos = new TreeMap<String, Sexo>();
		for (Sexo sexo : sexoService.listarTodos()) {
			listarSexos.put(sexo.getDescricao(), sexo);
		}

		listarEstadoCivis = new TreeMap<String, EstadoCivil>();
		for (EstadoCivil estadoCivil : estdocivilService.listarTodos()) {
			listarEstadoCivis.put(estadoCivil.getDescricao(), estadoCivil);
		}
		
		Long idEmpresa = Session.retornaIdEmpresa();

		listaServicos = new TreeMap<String, Servico>();
		for (Servico servico : servicoService.listarTodosPorEmpresa(idEmpresa)) {
			listaServicos.put(servico.getDescricao(), servico);
		}

	}

	public void handleFileUpload(FileUploadEvent event) {
		this.setArquivoUP(event.getFile());
	}

	public void onGeocode(GeocodeEvent event) throws SQLException {
		List<GeocodeResult> results = event.getResults();

		if (results != null && !results.isEmpty()) {
			LatLng center = results.get(0).getLatLng();
			centerGeoMap = center.getLat() + "," + center.getLng();

			for (int i = 0; i < results.size(); i++) {
				GeocodeResult result = results.get(i);
				geoModel.addOverlay(new Marker(result.getLatLng(), result.getAddress(), result.getLatLng().getLat()));

				this.enderecoCompleto = result.getAddress();
				this.latitude = result.getLatLng().getLat();
				this.longitude = result.getLatLng().getLng();
				this.num = alunoEdicao.getNumero();

				this.lt1 = (float) this.latitude;

				this.lt2 = (float) this.longitude;

				alunoEdicao.setLatitude(this.lt1);
				alunoEdicao.setLongitude(this.lt2);
				alunoEdicao.setEnderecoCompleto(this.enderecoCompleto);

			}

		}
	}

	public void cep() {

		try {

			this.cep = alunoEdicao.getCep();

			WebServiceCep webServiceCep = WebServiceCep.searchCep(this.cep);

			if (webServiceCep.wasSuccessful()) {

				alunoEdicao.setEndereco(webServiceCep.getLogradouroFull());
				alunoEdicao.setBairro(webServiceCep.getBairro());
				alunoEdicao.setCidade(webServiceCep.getCidade());
				alunoEdicao.setEstado(webServiceCep.getUf());

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage("Erro no processamento - Erro: " + ex.getMessage());
		}

	}

	public void alunoPDF() {

		try {
			Long idEmpresa = Session.retornaIdEmpresa();

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("idEmpresa", idEmpresa);

			listarAlunos = alunoService.listarTodosAlunosEmpresa(idEmpresa);
			relatorioService.gerarAlunoPDF(listarAlunos, (HashMap<String, Object>) parametros, "AlunoMapa2");
			
			consultar();
			preencheListasSelect();
			limpar();
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void limpar() {
		this.aluno = new Aluno();
	}

	public List<Aluno> getListarAlunos() {
		return listarAlunos;
	}

	public void setListarAlunos(List<Aluno> listarAlunos) {
		this.listarAlunos = listarAlunos;
	}

	public Aluno getAlunoEdicao() {
		return alunoEdicao;
	}

	public void setAlunoEdicao(Aluno alunoEdicao) {
		this.alunoEdicao = alunoEdicao;
	}

	public Aluno getAlunoSelecionada() {
		return alunoSelecionada;
	}

	public void setAlunoSelecionada(Aluno alunoSelecionada) {
		this.alunoSelecionada = alunoSelecionada;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Map<String, Sexo> getListarSexos() {
		return listarSexos;
	}

	public void setListarSexos(Map<String, Sexo> listarSexos) {
		this.listarSexos = listarSexos;
	}

	public Map<String, EstadoCivil> getListarEstadoCivis() {
		return listarEstadoCivis;
	}

	public void setListarEstadoCivis(Map<String, EstadoCivil> listarEstadoCivis) {
		this.listarEstadoCivis = listarEstadoCivis;
	}

	public Map<String, Servico> getListaServicos() {
		return listaServicos;
	}

	public void setListaServicos(Map<String, Servico> listaServicos) {
		this.listaServicos = listaServicos;
	}

	public UploadedFile getArquivoUP() {
		return arquivoUP;
	}

	public void setArquivoUP(UploadedFile arquivoUP) {
		this.arquivoUP = arquivoUP;
	}

	public MapModel getGeoModel() {
		return geoModel;
	}

	public void setGeoModel(MapModel geoModel) {
		this.geoModel = geoModel;
	}

	public String getCenterGeoMap() {
		return centerGeoMap;
	}

	public void setCenterGeoMap(String centerGeoMap) {
		this.centerGeoMap = centerGeoMap;
	}

	public String getEnderecoCompleto() {
		return enderecoCompleto;
	}

	public void setEnderecoCompleto(String enderecoCompleto) {
		this.enderecoCompleto = enderecoCompleto;
	}

	public MapModel getAdvancedModel() {
		return advancedModel;
	}

	public void setAdvancedModel(MapModel advancedModel) {
		this.advancedModel = advancedModel;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public float getLt1() {
		return lt1;
	}

	public void setLt1(float lt1) {
		this.lt1 = lt1;
	}

	public float getLt2() {
		return lt2;
	}

	public void setLt2(float lt2) {
		this.lt2 = lt2;
	}

}
