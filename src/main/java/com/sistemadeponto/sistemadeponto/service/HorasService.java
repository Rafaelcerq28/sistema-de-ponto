package com.sistemadeponto.sistemadeponto.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sistemadeponto.sistemadeponto.model.Funcionario;
import com.sistemadeponto.sistemadeponto.model.Horas;
import com.sistemadeponto.sistemadeponto.repository.FuncionarioRepository;
import com.sistemadeponto.sistemadeponto.repository.HorasRepository;

@Service
public class HorasService {

    
    public HorasService(HorasRepository horasRepository, FuncionarioRepository funcionarioRepository) {
        this.horasRepository = horasRepository;
        this.funcionarioRepository = funcionarioRepository;
    }
    private FuncionarioRepository funcionarioRepository;
    private HorasRepository horasRepository;
    
    //Cria as horas no banco de dados salvando a quantidade de horas trabalhadas
    public Horas criarHoras(Horas horas){

        //Armazena as horas trabalhadas no objeto
        horas.setHorasTrabalhadas(calculaDiffHoras(horas.getDataHoraEntrada(),horas.getDataHoraSaida()));
        return horasRepository.save(horas);
    }

    //Retorna todas as horas trabalhadas do banco
    public List<Horas> listarHoras(){
        return horasRepository.findAll();
    }
    
    //Metodo que pega apenas as horas trabalhadas e retorna elas somadas
    public String somarHorasTrabalhadas(Long id){
        Optional<Funcionario> optionalFuncionario =  funcionarioRepository.findById(id);
        
        //Checa se o funcionario existe
        if(optionalFuncionario.isPresent()){
        
            Funcionario funcionario = optionalFuncionario.get();
            List<String> horas = horasRepository.pegaHorasTrabalhadas(funcionario.getId());
            
            //Faz um update no funcionario
            funcionario.setHorasTrabalhadas(somaHoras(horas));
            funcionarioRepository.save(funcionario);
        
            return funcionario.getHorasTrabalhadas();
        
        }else{
            return "User not Found";
        }
    }
    
    //Method to read a List and sum the hours
    public String somaHoras(List<String> horas) {
        final DateFormat dt = new SimpleDateFormat("HH:mm");
        final Calendar c = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        
        long milliseconds = 0;
        c.clear();
        long startingMS = c.getTimeInMillis();
        for (final String t : horas) {
            try {
                milliseconds = milliseconds + (dt.parse(t).getTime() - startingMS);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Long horasSomadas = milliseconds / 1000 / 60 / 60 ;
        Long minutosSomados = (milliseconds / 1000 / 60) % 60;

        if(minutosSomados < 10){
            return horasSomadas + ":0" + minutosSomados;
        }else{
            return horasSomadas + ":" + minutosSomados;
        }
    }
    
    //Metodos para calcular as horas trabalhadas
    public String calculaDiffHoras(String dataEntrada, String dataSaida){
        try{
            //pattern to format the input
            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");//"01-01-2023 15:00:00"

            //dateTime1 receive the formated data
            LocalDateTime dateTimeEntrada= LocalDateTime.parse(dataEntrada, f);
            LocalDateTime dateTimeSaida= LocalDateTime.parse(dataSaida, f);

            //Durarion.between calculate the difference between entrada and saida in hours and minutes
            long diferencaHor = Duration.between(dateTimeEntrada, dateTimeSaida).toHours();
            long diferencaMin = Duration.between(dateTimeEntrada, dateTimeSaida).toMinutes() % 60;
            
            //Checa if the the dateTimeSaida is > than dateTimeEntrada
            if(diferencaHor < 0 || diferencaMin < 0){
                throw new Exception("Fail");
            }
            
            //Proccess to format the hours and minutes in the format HH:mm insted of H:mm
            String diferencaHoraFormatada = Long.toString(diferencaHor);
            String diferencaMinFormatado = Long.toString(diferencaMin);
            
            //Insert a 0 if the number is below 10 to apply the pattern HH:mm
            if(diferencaHor < 10){
                String concatenar = "0";
                diferencaHoraFormatada = concatenar.concat(diferencaHoraFormatada);
            }
            if(diferencaMin <10){
                String concatenar = "0";
                diferencaMinFormatado = concatenar.concat(diferencaMinFormatado);
            }
            
            //Saving the hours in a String
            String horasTrabalhadas = diferencaHoraFormatada + ":" + diferencaMinFormatado;

            return horasTrabalhadas;
        }catch (Exception e) {
            return "00:00";
        }
    }   

}
