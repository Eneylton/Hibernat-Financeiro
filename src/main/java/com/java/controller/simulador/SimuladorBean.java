package com.java.controller.simulador;

import java.io.Serializable;
import java.text.ParseException;
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

import com.java.modelo.Agenda2;
import com.java.modelo.Aluno;
import com.java.service.Agenda2Service;
import com.java.service.AlunoService;
import com.java.service.EmpresaService;
import com.java.util.NegocioException;
import com.java.util.Session;
import com.java.util.jsf.FacesUtil;

@Named
@ViewScoped
public class SimuladorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Agenda2Service agendaService;

	@Inject
	private AlunoService alunoService;
	
	@Inject
	private EmpresaService empresaService;

	private List<Agenda2> listarAgendas = new ArrayList<>();

	private List<Agenda2> listarAgendaAlunoID = new ArrayList<>();

	private ScheduleModel agendaCalendario;

	private Agenda2 agenda;

	private Agenda2 agendaSelecionada;

	private Aluno aluno = new Aluno();

	@PostConstruct
	public void inicializar() throws ParseException, NumberFormatException, NegocioException {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
		
	    Long idEmpresa = Session.retornaIdEmpresa();

		try {

			String idAluno = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("aluno");

			if (idAluno != null) {

				aluno.setId(Long.parseLong(idAluno));

			} else {
				System.out.println("Não Nullo --> " + idAluno);

			}

			agendaCalendario = new DefaultScheduleModel();

			listarAgendas = agendaService.listarTodosPorEmpresa(idEmpresa);

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.addErrorMessage(ex.getMessage());
		}

		for (Agenda2 ev1 : listarAgendas) {

			DefaultScheduleEvent evt = new DefaultScheduleEvent();
			evt.setData(ev1.getId());
			evt.setTitle(ev1.getAluno().getNome());
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

		for (Agenda2 ev2 : listarAgendas) {
			if (ev2.getId() == (Long) event.getData()) {

				agenda = ev2;
				break;
			}
		}
	}

	public void quandoNovo(SelectEvent selectEvent) throws NumberFormatException, NegocioException, ParseException {

		/* QUANDO ESTIVER EM HÓRARIO DE VERÃO */

		/*
		 * ScheduleEvent event = new DefaultScheduleEvent("", (Date)
		 * selectEvent.getObject(), (Date) selectEvent.getObject());
		 * 
		 * Date horaInicio = new java.sql.Date(event.getStartDate().getTime());
		 * Date horaFim = new java.sql.Date(event.getEndDate().getTime());
		 * 
		 * agenda = new Agenda2(); agenda.setInicio(new
		 * Date(horaInicio.getTime() + 60 * 60 * 1000)); agenda.setFim(new
		 * Date(horaFim.getTime() + 60 * 60 * 1000));
		 */

		ScheduleEvent event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(),
				(Date) selectEvent.getObject());
		agenda = new Agenda2();

		agenda.setInicio(new java.sql.Date(event.getStartDate().getTime()));
		agenda.setFim(new java.sql.Date(event.getEndDate().getTime()));

	}

	public void quandoMovido(ScheduleEntryMoveEvent movi) {
		for (Agenda2 ag : listarAgendas) {

			if (ag.getId() == (Long) movi.getScheduleEvent().getData()) {

				agenda = ag;

				try {
					if (listarAgendas.size() <= 0) {
						agendaService.salvar(agenda);
						inicializar();
					} else {

					}

				} catch (Exception ex) {
					ex.printStackTrace();
					FacesUtil.addErrorMessage(ex.getMessage());
				}
				break;

			}

		}
	}

	public void quandoRedimencionado(ScheduleEntryResizeEvent movi) {

		for (Agenda2 ag : listarAgendas) {

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
					Long idAluno = aluno.getId();
					
					Long idEmpresa = Session.retornaIdEmpresa();

					agenda.setAluno(alunoService.retornarAlunoPorID(idAluno));
					agenda.setEmpresa(empresaService.retornarEmpresaPorID(idEmpresa));
					
					listarAgendaAlunoID = agendaService.listarTodosIDAluno(idAluno);

					if (listarAgendaAlunoID.size() <= 0) {

						agendaService.salvar(agenda);
						inicializar();
						FacesUtil.addSuccessMessage("Evento  Cadastrado com sucesso!");

					} else {
						FacesUtil.addErrorMessage("Exedeu o numero máximo de reserva !!!!");
					}

				} catch (Exception ex) {
					ex.printStackTrace();
					FacesUtil.addErrorMessage(ex.getMessage());
				}

				agenda = new Agenda2();

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

	public ScheduleModel getAgendaCalendario() {
		return agendaCalendario;
	}

	public void setAgendaCalendario(ScheduleModel agendaCalendario) {
		this.agendaCalendario = agendaCalendario;
	}

	public List<Agenda2> getListarAgendas() {
		return listarAgendas;
	}

	public void setListarAgendas(List<Agenda2> listarAgendas) {
		this.listarAgendas = listarAgendas;
	}

	public Agenda2 getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda2 agenda) {
		this.agenda = agenda;
	}

	public Agenda2 getAgendaSelecionada() {
		return agendaSelecionada;
	}

	public void setAgendaSelecionada(Agenda2 agendaSelecionada) {
		this.agendaSelecionada = agendaSelecionada;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public List<Agenda2> getListarAgendaAlunoID() {
		return listarAgendaAlunoID;
	}

	public void setListarAgendaAlunoID(List<Agenda2> listarAgendaAlunoID) {
		this.listarAgendaAlunoID = listarAgendaAlunoID;
	}

}
