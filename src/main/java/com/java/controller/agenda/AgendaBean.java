package com.java.controller.agenda;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.java.modelo.Agenda;
import com.java.modelo.CadastroAgenda;
import com.java.service.AgendaService;
import com.java.service.CadastroAgendaService;
import com.java.service.EmpresaService;
import com.java.util.NegocioException;
import com.java.util.Session;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class AgendaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AgendaService agendaService;

	@Inject
	private EmpresaService empresaService;

	@Inject
	CadastroAgendaService cadastroAgendaService;

	private List<Agenda> listarAgendas = new ArrayList<>();

	private List<Agenda> listarAgendaIdaluno = new ArrayList<>();

	private ScheduleModel agendaCalendario;

	private Agenda agenda;

	private Agenda agendaSelecionada;

	private CadastroAgenda cadastroAgenda = new CadastroAgenda();

	@PostConstruct
	public void inicializar() throws ParseException, NumberFormatException, NegocioException {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
		
	    Long idEmpresa = Session.retornaIdEmpresa();

		try {

			String idAgenda = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("agenda");

			if (idAgenda != null) {

				cadastroAgenda.setId(Long.parseLong(idAgenda));

			} else {
				System.out.println("Não Nullo --> " + idAgenda);

			}

			agendaCalendario = new DefaultScheduleModel();

			listarAgendas = agendaService.listarTodosPorEmpresa(idEmpresa);

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage(ex.getMessage());
		}

		for (Agenda ev1 : listarAgendas) {

			DefaultScheduleEvent evt = new DefaultScheduleEvent();
			evt.setData(ev1.getId());
			evt.setTitle(ev1.getCadastroAgenda().getAluno().getNome() + " - "
					+ ev1.getCadastroAgenda().getVeiculo().getEspecie());
			evt.setStartDate(ev1.getInicio());
			evt.setEndDate(ev1.getFim());

			evt.setDescription(ev1.getDescricao());
			evt.setEditable(true);

			if (ev1.isStatus() == true) {
				evt.setStyleClass("cor1");
			} else if (ev1.isStatus() == false) {
				evt.setStyleClass("cor2");
			}

			agendaCalendario.addEvent(evt);

		}

	}

	public void quandoSelecionado(SelectEvent selectEvent) {
		ScheduleEvent event = (ScheduleEvent) selectEvent.getObject();

		for (Agenda ev2 : listarAgendas) {
			if (ev2.getId() == (Long) event.getData()) {

				agenda = ev2;
				break;
			}
		}
	}

	public void quandoNovo(SelectEvent selectEvent) throws NumberFormatException, NegocioException, ParseException {

		/* QUANDO ESTIVER NO HORÁRIO DE VERÃO */

		/*
		 * ScheduleEvent event = new DefaultScheduleEvent("", (Date)
		 * selectEvent.getObject(), (Date) selectEvent.getObject());
		 * 
		 * Date horaInicio = new java.sql.Date(event.getStartDate().getTime());
		 * Date horaFim = new java.sql.Date(event.getEndDate().getTime());
		 * 
		 * agenda = new Agenda(); agenda.setInicio(new Date(horaInicio.getTime()
		 * + 60 * 60 * 1000)); agenda.setFim(new Date(horaFim.getTime() + 60 *
		 * 60 * 1000));
		 */

		ScheduleEvent event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(),
				(Date) selectEvent.getObject());
		agenda = new Agenda();
		agenda.setInicio(new java.sql.Date(event.getStartDate().getTime()));
		agenda.setFim(new java.sql.Date(event.getEndDate().getTime()));

	}

	public void quandoMovido(ScheduleEntryMoveEvent movi) {
		for (Agenda ag : listarAgendas) {

			if (ag.getId() == (Long) movi.getScheduleEvent().getData()) {

				agenda = ag;

				try {

					agendaService.salvar(agenda);
					inicializar();

				} catch (Exception ex) {
					ex.printStackTrace();
					FacesUtil.addErrorMessage(ex.getMessage());
				}
				break;

			}

		}
	}

	public void quandoRedimencionado(ScheduleEntryResizeEvent movi) {

		for (Agenda ag : listarAgendas) {

			if (ag.getId() == (Long) movi.getScheduleEvent().getData()) {

				agenda = ag;

				try {

					agendaService.salvar(agenda);
					inicializar();

				} catch (Exception ex) {
					ex.printStackTrace();
					FacesUtil.addErrorMessage(ex.getMessage());
				}
				break;

			}

		}

	}

	public void salvar() {
		if (agenda.getId() == null) {
			if (agenda.getInicio().getTime() <= agenda.getFim().getTime()) {

				try {
					Long idEmpresa = Session.retornaIdEmpresa();

					SimpleDateFormat dat = new SimpleDateFormat("yyyy-MM-dd");
					String dataAgendaInformada = dat.format(agenda.getInicio());

					SimpleDateFormat dataInformada = new SimpleDateFormat("HH:mm");
					String dataFormatada = dataInformada.format(agenda.getInicio());

					Long idAgenda = cadastroAgenda.getId();

					agenda.setCadastroAgenda(cadastroAgendaService.retornarCadastroAgendaPorID(idAgenda));
					agenda.setEmpresa(empresaService.retornarEmpresaPorID(idEmpresa));

					int totalAlunos = agendaService.listarTotalAlunos(idAgenda);

					Long idAluno = agenda.getCadastroAgenda().getAluno().getId();
					Long idInstrutor = agenda.getCadastroAgenda().getInstrutor().getId();

					for (Agenda ag : listarAgendas) {

						SimpleDateFormat dataDb = new SimpleDateFormat("HH:mm");
						String dataBanco = dataDb.format(ag.getInicio());
						SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");
						String dataAgendaBanco = data.format(ag.getInicio());

						Long alunoBanco = ag.getCadastroAgenda().getAluno().getId();
						Long instrutorBanco = ag.getCadastroAgenda().getInstrutor().getId();

						if (alunoBanco != idAluno

								&& instrutorBanco == idInstrutor

								&& dataBanco.equals(dataFormatada)

								&& dataAgendaBanco.equals(dataAgendaInformada)) {

							FacesUtil.addErrorMessage("Horário reservado para este instrutor !!!");

							return;

						}
					}

					if(totalAlunos <= 4){
					agendaService.salvar(agenda);
					inicializar();
					FacesUtil.addSuccessMessage("Evento  Cadastrado com sucesso!");
					
					}else{
						FacesUtil.addErrorMessage("Excedeu o número máximo de reserva para este aluno !!!");
					}

				} catch (Exception ex) {
					ex.printStackTrace();
					FacesUtil.addErrorMessage(ex.getMessage());
				}

				agenda = new Agenda();

			} else {
				FacesUtil.addSuccessMessage("Agenda salvo com sucesso!");
			}

		} else {

			try {
				agendaService.salvar(agenda);
				inicializar();
			} catch (Exception ex) {
				ex.printStackTrace();
				FacesUtil.addErrorMessage(ex.getMessage());
			}
		}
	}

	public void excluir() {

		try {
			agendaService.excluir(agendaSelecionada);
			this.listarAgendas.remove(agendaSelecionada);
			FacesUtil.addSuccessMessage("Evento " + agendaSelecionada.getDescricao() + " excluída com sucesso.");
		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage(ex.getMessage());
		}

	}

	private final Date now = new Date();

	public Date getNow() {
		return now;
	}

	public List<Agenda> getListarAgendas() {
		return listarAgendas;
	}

	public void setListarAgendas(List<Agenda> listarAgendas) {
		this.listarAgendas = listarAgendas;
	}

	public ScheduleModel getAgendaCalendario() {
		return agendaCalendario;
	}

	public void setAgendaCalendario(ScheduleModel agendaCalendario) {
		this.agendaCalendario = agendaCalendario;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public Agenda getAgendaSelecionada() {
		return agendaSelecionada;
	}

	public void setAgendaSelecionada(Agenda agendaSelecionada) {
		this.agendaSelecionada = agendaSelecionada;
	}

	public CadastroAgenda getCadastroAgenda() {
		return cadastroAgenda;
	}

	public void setCadastroAgenda(CadastroAgenda cadastroAgenda) {
		this.cadastroAgenda = cadastroAgenda;
	}

	public List<Agenda> getListarAgendaIdaluno() {
		return listarAgendaIdaluno;
	}

	public void setListarAgendaIdaluno(List<Agenda> listarAgendaIdaluno) {
		this.listarAgendaIdaluno = listarAgendaIdaluno;
	}

}
