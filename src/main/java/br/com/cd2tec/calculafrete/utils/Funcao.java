package br.com.cd2tec.calculafrete.utils;

import br.com.cd2tec.calculafrete.controllers.dtos.Cep.ConsultaCepDto;
import br.com.cd2tec.calculafrete.controllers.dtos.frete.FretePersistDto;
import br.com.cd2tec.calculafrete.controllers.dtos.frete.FreteInputDto;
import br.com.cd2tec.calculafrete.exceptions.RegraDeNegocioException;
import br.com.cd2tec.calculafrete.services.ViaCepFeignClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Service
public class Funcao {

    private final ModelMapper modelMapper;
    private final ViaCepFeignClient viaCepFeignClient;

    @Autowired
    public Funcao(
            ModelMapper modelMapper,
            ViaCepFeignClient viaCepFeignClient) {
        this.modelMapper = modelMapper;
        this.viaCepFeignClient = viaCepFeignClient;
    }



    public FretePersistDto consultarFrete(FreteInputDto freteInputDto){

        var cepOrigem =  buscarCep(freteInputDto.getCepOrigem());

        var cepDestino =  buscarCep(freteInputDto.getCepDestino());

        return aplicarRegraDeNegocio(cepOrigem, cepDestino, freteInputDto);

    }

    private FretePersistDto aplicarRegraDeNegocio(ConsultaCepDto cepOrigem, ConsultaCepDto cepDestino, FreteInputDto freteInputDto){

        if(cepOrigem.getDdd().equals(cepDestino.getDdd()) && cepOrigem.getUf().equals(cepDestino.getUf())){
            return regraDeNegocio(freteInputDto, 1);
        }

        if(cepOrigem.getDdd().equals(cepDestino.getDdd())){
            return regraDeNegocio(freteInputDto, 1);
        }

        if(cepOrigem.getUf().equals(cepDestino.getUf())){
            return regraDeNegocio(freteInputDto, 2);
        } else {
            return regraDeNegocio(freteInputDto, 3);
        }
    }

    private LocalDate ConvertHojeEmMaisDias(int dias){

        Calendar cal = Calendar.getInstance();

        Date date = modelMapper.map(LocalDate.now(), Date.class);

        cal.setTime(date);
        cal.add(Calendar.DATE, dias);
        date = cal.getTime();

        return date.toInstant().atZone( ZoneId.systemDefault()).toLocalDate();
    }

    private ConsultaCepDto buscarCep(String cepInput){

        String cep = cepInput.replaceAll("[^0-9]", "").replaceAll(" ","");

        if(cep.length() != 8){
            throw new RegraDeNegocioException("cep.invalido");
        }

        ConsultaCepDto cepReturn = this.viaCepFeignClient.buscaCep(cep).getBody();

        if(Objects.requireNonNull(cepReturn).getCep() == null){
            throw new RegraDeNegocioException("cep.invalido");
        }
        return cepReturn;
    }

    public static String formatarString(String value, String pattern) {
        MaskFormatter mf;
        try {
            mf = new MaskFormatter(pattern);
            mf.setValueContainsLiteralCharacters(false);
            return mf.valueToString(value);
        } catch (ParseException ex) {
            return value;
        }
    }

    private FretePersistDto regraDeNegocio(FreteInputDto frete, int condicao){

        var freteInputDto = modelMapper.map(frete, FretePersistDto.class);
        freteInputDto.setVlTotalFrete(freteInputDto.getPeso());

        switch (condicao){
            case 1:
                freteInputDto.setVlTotalFrete(freteInputDto.getVlTotalFrete() - (freteInputDto.getVlTotalFrete() * 0.50));
                freteInputDto.setDataPrevistaEntrega(ConvertHojeEmMaisDias(1));
                freteInputDto.setDataConsulta(LocalDate.now());
                freteInputDto.setCepOrigem(formatarString(freteInputDto.getCepOrigem(), "#####-###"));
                freteInputDto.setCepDestino(formatarString(freteInputDto.getCepDestino(), "#####-###"));

                return freteInputDto;

            case 2:
                freteInputDto.setVlTotalFrete(freteInputDto.getVlTotalFrete() - (freteInputDto.getVlTotalFrete() * 0.75));
                freteInputDto.setDataPrevistaEntrega(ConvertHojeEmMaisDias(3));
                freteInputDto.setDataConsulta(LocalDate.now());
                freteInputDto.setCepOrigem(formatarString(freteInputDto.getCepOrigem(), "#####-###"));
                freteInputDto.setCepDestino(formatarString(freteInputDto.getCepDestino(), "#####-###"));

                return freteInputDto;

            case 3:
                freteInputDto.setDataPrevistaEntrega(ConvertHojeEmMaisDias(10));
                freteInputDto.setDataConsulta(LocalDate.now());
                freteInputDto.setCepOrigem(formatarString(freteInputDto.getCepOrigem(), "#####-###"));
                freteInputDto.setCepDestino(formatarString(freteInputDto.getCepDestino(), "#####-###"));

                return freteInputDto;
        }

        return null;
    }
}
