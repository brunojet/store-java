## Projeto Store Java

### Objetivo

- Fornecer uma plataforma modular para cadastro, certificação, distribuição e operação de aplicativos em uma loja de aplicativos.
- Público-alvo: parceiros integradores, time de operações e clientes com terminais POS.

### Módulos previstos

- **MainApp:** Módulo principal que inicia a aplicação Spring Boot e configura os componentes essenciais.
  - **Componentes previstos:**
    - Classe de inicialização Spring Boot
    - Configurações globais
- **Distribution:** Módulo encarregado do cadastro do aplicativo na loja, informações para os perfis (vitrine), gerenciamento de versões e distribuição para usuários finais.
  - **Consumidor:** Aplicação que atende parceiros.
  - **Componentes previstos:**
    - Controlador REST
    - DTOs
    - Serviços de negócio
- **Delivery:** Módulo encarregado da entrega dos metadados da versão do aplicativo certificado para que possa ser distribuído posteriormente em estágios, conforme determinado pelo parceiro.
  - **Consumidor:** Aplicação que trata o ciclo de certificação do aplicativo.
  - **Componentes previstos:**
    - Controlador REST
    - DTOs
    - Serviços de negócio
- **Office:** Módulo encarregado do gerenciamento das operações administrativas e de backoffice relacionadas aos aplicativos.
  - **Consumidor:** Time de operações da loja de aplicativos.
  - **Componentes previstos:**
    - Controlador REST
    - DTOs
    - Serviços de negócio
- **Client:** Módulo responsável pela interface e interação com o cliente final, incluindo funcionalidades específicas para o usuário.
  - **Consumidor:** Terminais POS do cliente final (ponto de venda).
  - **Componentes previstos:**
    - Controlador REST
    - DTOs
    - Serviços de negócio
- **Common:** Módulo compartilhado entre os outros módulos, contendo componentes reutilizáveis e utilitários comuns.
  - **Consumidores:** Módulos `MainApp`, `Distribution`, `Delivery`, `Office` e `Client`.
  - **Componentes previstos:**
    - Modelos de dados
    - Repositórios JPA
    - Utilitários
    - Logs

### Casos de uso principais

- Cadastrar aplicativo, metadados da vitrine, metadados por um parceiro, gerenciar distribuição de aplicativos em estágios.
- Disponibilizar versão certificada para distribuição em estágios.
- POS consultar e baixar metadados/artefatos para instalação.

### Entidades mapeadas pelo modelo físico

- `application` — dados do app (nome, descrição, ativo, timestamps).
- `application_contact` — contatos relacionados ao aplicativo (site, email, phone).
- `application_detail` — descrição/metadata adicional do aplicativo.
- `application_profile_history` — histórico de perfil/certificação (review_at, production_at, causas de desativação).
- `application_profile_history_category` — categorias associadas ao perfil histórico.
- `application_configuration` — configuração por combinação `integration_type` + `terminal_model` para uma `application`.
- `application_profile_history_configuration` — snapshot de configuração vinculada a um perfil histórico.
- `application_version` — versões do aplicativo (version_name, version_code, size, image_id, pilot/production timestamps).
- `application_catalog` — registro de disponibilização por `stage` (catalog/distribution record).
- `storage_object`, `image`, `video` — objetos de armazenamento e mídias relacionadas (imagens/vídeos).
- `category`, `category_type` — categorias e tipos de categorias para vitrine/classificação.
- `integration_type`, `terminal_model` — tipos de integração e modelos de terminais suportados.
- `tipo_filtro`, `filtro` — filtros e tipos de filtro usados para seleção/vitrine.

Observações:
- A modelagem atual não possui uma tabela `partner` explícita; parte das informações de contato está em `application_contact` e o histórico de certificação está em `application_profile_history`. Se desejar modelar parceiros com credenciais e permissões separadas, recomendo adicionar uma tabela `partner` e referenciar `application.partner_id`.
- Se for importante guardar evidências (arquivos) do processo de certificação, sugiro vincular `storage_object` diretamente a `application_profile_history` ou criar uma tabela `certification_evidence`.

### Recomendações iniciais

- **Autenticação/Autorização:** OAuth2/JWT para APIs internas e comunicação entre módulos.
- **Versionamento de API:** `/api/v1/...` com estratégia para versões futuras.
- **CI/CD:** Pipeline para build, testes automatizados, segurança e deploy (staging → produção).
- **Não-funcionais:** definir SLAs, requisitos de performance e limites de throughput para downloads.
- **Observabilidade:** logs estruturados, métricas e alertas (Prometheus/Grafana, ELK).
- **Documentação & Diagramas:** adicionar diagrama de componentes e um ER mínimo para as entidades centrais.

### Próximos passos sugeridos

- Definir requisitos funcionais detalhados (user stories / casos de uso estendidos).
- Criar diagramas (componentes, sequência e ER).
- Propor contrato de API (OpenAPI) para cada módulo exposto.
- Mapear políticas de segurança e operações (rotinas de deploy, rollback, backups).
