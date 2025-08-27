# 🚗 Você Aluga - Sistema de Aluguel de Veículos

Um sistema completo de aluguel de veículos desenvolvido com Spring Boot (backend) e React + TypeScript (frontend).

## 📋 Sobre o Projeto

O **Você Aluga** é uma plataforma digital para gerenciamento de aluguel de veículos que oferece:

- 🔐 Sistema de autenticação e autorização
- 👥 Gestão de usuários (motoristas e administradores)
- 🚙 Catálogo de veículos disponíveis
- 📅 Sistema de reservas e agendamentos
- 💳 Processamento de pagamentos
- 📊 Dashboard administrativo
- 📱 Interface responsiva e moderna

## 🛠️ Tecnologias Utilizadas

### Backend

- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.3** - Framework principal
- **Spring Security** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados
- **Spring Validation** - Validação de dados
- **Maven** - Gerenciamento de dependências

### Frontend

- **React 19.1.0** - Biblioteca para interface
- **TypeScript** - Tipagem estática
- **Vite** - Build tool e dev server
- **React Router Dom** - Roteamento
- **Axios** - Cliente HTTP
- **TanStack Query** - Gerenciamento de estado
- **GSAP** - Animações
- **JWT Decode** - Decodificação de tokens
- **SCSS** - Pré-processador CSS

### Documentação

- **PlantUML** - Diagramas de arquitetura e modelagem

## 📁 Estrutura do Projeto

```
voce-aluga/
├── backend/                    # API Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/       # Código fonte Java
│   │   │   └── resources/      # Configurações e recursos
│   │   └── test/               # Testes unitários
│   └── pom.xml                 # Dependências Maven
├── frontend/                   # Aplicação React
│   ├── src/
│   │   ├── components/         # Componentes reutilizáveis
│   │   ├── pages/              # Páginas da aplicação
│   │   ├── services/           # Serviços de API
│   │   ├── routes/             # Configuração de rotas
│   │   ├── types/              # Tipos TypeScript
│   │   └── utils/              # Utilitários
│   └── package.json            # Dependências NPM
├── *.puml                      # Diagramas PlantUML
└── README.md                   # Este arquivo
```

## 🚀 Como Executar o Projeto

### Pré-requisitos

- **Java 21** ou superior
- **Node.js 18** ou superior
- **Maven 3.8** ou superior
- **Git**

### Configuração do Backend

1. **Clone o repositório:**

```bash
git clone <url-do-repositorio>
cd voce-aluga
```

2. **Configure as variáveis de ambiente:**

```bash
export DB_AMBIENT=dev  # ou hmg, test
export MAIL_HOST=smtp.gmail.com
export MAIL_PORT=587
export MAIL_USERNAME=seu-email@gmail.com
export MAIL_PASSWORD=sua-senha-app
```

3. **Execute o backend:**

```bash
cd backend
./mvnw spring-boot:run
```

O backend estará disponível em: `http://localhost:8080`

### Configuração do Frontend

1. **Instale as dependências:**

```bash
cd frontend
npm install
```

2. **Execute o frontend:**

```bash
npm start
```

O frontend estará disponível em: `http://localhost:5173`

## 🔧 Scripts Disponíveis

### Backend

```bash
# Compilar o projeto
./mvnw compile

# Executar testes
./mvnw test

# Gerar o JAR
./mvnw package

# Executar a aplicação
./mvnw spring-boot:run
```

### Frontend

```bash
# Iniciar servidor de desenvolvimento
npm start

# Fazer build para produção
npm run build

# Executar linter
npm run lint

# Preview do build
npm run preview
```

## 📊 Diagramas e Documentação

O projeto inclui diagramas PlantUML para documentação da arquitetura:

- `diagrama-arquitetura.puml` - Arquitetura geral do sistema
- `diagrama-classes.puml` - Diagrama de classes
- `diagrama-pacotes.puml` - Estrutura de pacotes
- `diagrama-sequencia-reserva.puml` - Fluxo de reservas

Para exportar os diagramas, use o comando do VS Code:

```
Ctrl+Shift+P > PlantUML: Export Workspace Diagrams
```

## 🌍 Ambientes

O projeto suporta múltiplos ambientes:

- **dev** - Desenvolvimento local
- **hmg** - Homologação
- **test** - Testes

Configure através da variável `DB_AMBIENT`.

## 🤝 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Funcionalidades

### Para Usuários

- ✅ Cadastro e login de usuários
- ✅ Navegação de veículos disponíveis
- ✅ Sistema de reservas
- ✅ Acompanhamento de reservas
- ✅ Processamento de pagamentos
- ✅ Perfil do usuário

### Para Administradores

- ✅ Dashboard administrativo
- ✅ Gestão de veículos
- ✅ Gestão de usuários
- ✅ Relatórios de reservas
- ✅ Configurações do sistema

## 📞 Contato

Desenvolvido por [Alex Cordeiro](https://github.com/alexsanjr) e [Levy Arthur](https://github.com/levyath)

Projeto: [voce-aluga](https://github.com/alexsanjr/voce-aluga)

---

⭐ Se este projeto foi útil para você, considere dar uma estrela no repositório!
