package com.springbatch.simplepartitionerlocal.reader;

import com.springbatch.simplepartitionerlocal.config.ArquivoPartitioner;
import com.springbatch.simplepartitionerlocal.dominio.Pessoa;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.springbatch.simplepartitionerlocal.dominio.DadosBancarios;

@Configuration
public class ArquivoDadosBancariosReaderConfig {
    @Autowired
    private ArquivoPartitioner partitioner;

    @StepScope
    @Bean
    public CustomArquivoReader<DadosBancarios> arquivoDadosBancariosaReader(@Value("#{stepExecutionContext['particao']}")
                                                           Integer particao){
        return new CustomArquivoReader<DadosBancarios>(dadosBancariosReader(partitioner.calcularPrimeiroItemLeitura(particao)),
                partitioner.getItensLimit());
    }

	public FlatFileItemReader<DadosBancarios> dadosBancariosReader(int currentItemCount) {
		return new FlatFileItemReaderBuilder<DadosBancarios>()
				.name("dadosBancariosReader")
				.resource(new FileSystemResource("files/dados_bancarios.csv"))
				.delimited()
				.names("pessoaId", "agencia", "conta", "banco", "id")
				.addComment("--")
                .currentItemCount(currentItemCount)
				.targetType(DadosBancarios.class)
				.build();
	}
}
