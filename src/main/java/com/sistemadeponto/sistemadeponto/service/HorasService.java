package com.sistemadeponto.sistemadeponto.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sistemadeponto.sistemadeponto.model.Horas;
import com.sistemadeponto.sistemadeponto.repository.HorasRepository;

@Service
public class HorasService {

    
    public HorasService(HorasRepository horasRepository) {
        this.horasRepository = horasRepository;
    }

    private HorasRepository horasRepository;
    
    public Horas criarHoras(Horas horas){

        //Armazena as horas trabalhadas no objeto
        horas.setHorasTrabalhadas(calculaHoras(horas.getDataHoraEntrada(),horas.getDataHoraSaida()));

        return horasRepository.save(horas);
    }

    public List<Horas> listarHoras(){
        return horasRepository.findAll();
    }

    //Metodos para calcular as horas trabalhadas
    public String calculaHoras(String dataEntrada, String dataSaida){
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
