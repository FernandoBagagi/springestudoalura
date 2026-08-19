INSERT INTO
    usuarios (id, login, email, senha, perfil)
VALUES
    (
        1,
        'marcos.almeida',
        'marcos.almeida@estudo.spring',
        '$2a$10$q9xUw9JIJpO2aDJCHPfcFuVH2HsRBqCfOLaTa9vzF.2DvZsnBtXKS',
        'PACIENTE'
    ),
    (
        2,
        'ana.pereira',
        'ana.pereira@estudo.spring',
        '$2a$10$q9xUw9JIJpO2aDJCHPfcFuVH2HsRBqCfOLaTa9vzF.2DvZsnBtXKS',
        'PACIENTE'
    ),
    (
        3,
        'beatriz.lima',
        'beatriz.lima@estudo.spring',
        '$2a$10$q9xUw9JIJpO2aDJCHPfcFuVH2HsRBqCfOLaTa9vzF.2DvZsnBtXKS',
        'MEDICO'
    ),
    (
        4,
        'joao.pereira',
        'joao.pereira@estudo.spring',
        '$2a$10$q9xUw9JIJpO2aDJCHPfcFuVH2HsRBqCfOLaTa9vzF.2DvZsnBtXKS',
        'MEDICO'
    ),
    (
        5,
        'mariana.ribeiro',
        'mariana.ribeiro@estudo.spring',
        '$2a$10$q9xUw9JIJpO2aDJCHPfcFuVH2HsRBqCfOLaTa9vzF.2DvZsnBtXKS',
        'MEDICO'
    ),
    (
        6,
        'atendente',
        'atendente@estudo.spring',
        '$2a$10$q9xUw9JIJpO2aDJCHPfcFuVH2HsRBqCfOLaTa9vzF.2DvZsnBtXKS',
        'ATENDENTE'
    );

INSERT INTO
    medicos (id, genero, nome, sobrenome, crm, especialidade)
VALUES
    (
        3,
        'FEMININO',
        'Beatriz',
        'Lima',
        'CRM/UF 482391',
        'GINECOLOGIA'
    ),
    (
        4,
        'MASCULINO',
        'João',
        'Pereira',
        'CRM/UF 593021',
        'GINECOLOGIA'
    ),
    (
        5,
        'FEMININO',
        'Mariana',
        'Ribeiro',
        'CRM/UF 774512',
        'GINECOLOGIA'
    );

INSERT INTO
    pacientes (
        id,
        nome,
        cpf,
        nascimento,
        telefone,
        logradouro,
        numero,
        complemento,
        bairro,
        cidade,
        uf,
        cep
    )
VALUES
    (
        1,
        'Marcos Vinicius Almeida',
        '111.111.111-11',
        '2001-10-25',
        '21 77845-3321',
        'Avenida Atlântica',
        '1702',
        'Próximo ao Posto 4',
        'Copacabana',
        'Rio de Janeiro',
        'RJ',
        '22010-000'
    ),
    (
        2,
        'Ana Carolina Costa da Silva Pereira',
        '222.222.222-22',
        '1999-12-31',
        '11 76712-8894',
        'Praça da Sé',
        NULL,
        NULL,
        'Sé',
        'São Paulo',
        'SP',
        '01001-000'
    );