USE [li4]
GO

																	/****** USER ******/


SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[utilizador](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[handle] [nvarchar](20) NOT NULL,
	[birth_date] [date] NOT NULL,
	[password] [nvarchar](50) NOT NULL,
	[email] [nvarchar](50) NOT NULL,
	[nationality] [nchar](3) NOT NULL,
 CONSTRAINT [PK_utilizador] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO


																	/****** ADMIN ******/


SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[admin](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_user] [int] NOT NULL,
 CONSTRAINT [PK_admin] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[admin]  WITH CHECK ADD  CONSTRAINT [FK_admin_utilizador] FOREIGN KEY([id_user])
REFERENCES [dbo].[utilizador] ([id])
GO

ALTER TABLE [dbo].[admin] CHECK CONSTRAINT [FK_admin_utilizador]
GO


																	/****** VENDEDOR ******/


SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[vendedor](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_user] [int] NOT NULL,
 CONSTRAINT [PK_vendedor] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[vendedor]  WITH CHECK ADD  CONSTRAINT [FK_vendedor_utilizador] FOREIGN KEY([id_user])
REFERENCES [dbo].[utilizador] ([id])
GO

ALTER TABLE [dbo].[vendedor] CHECK CONSTRAINT [FK_vendedor_utilizador]
GO


																	/****** COMPRADOR ******/


SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[comprador](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_user] [int] NOT NULL,
 CONSTRAINT [PK_comprador] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[comprador]  WITH CHECK ADD  CONSTRAINT [FK_comprador_utilizador] FOREIGN KEY([id_user])
REFERENCES [dbo].[utilizador] ([id])
GO

ALTER TABLE [dbo].[comprador] CHECK CONSTRAINT [FK_comprador_utilizador]
GO


																	/****** DENUNCIA ******/


SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[denuncia](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[motivo] [nvarchar](50) NOT NULL,
	[id_denunciado] [int] NOT NULL,
	[id_denunciador] [int] NOT NULL,
 CONSTRAINT [PK_denuncia] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[denuncia]  WITH CHECK ADD  CONSTRAINT [FK_denunciado_utilizador] FOREIGN KEY([id_denunciado])
REFERENCES [dbo].[utilizador] ([id])
GO

ALTER TABLE [dbo].[denuncia] CHECK CONSTRAINT [FK_denunciado_utilizador]
GO

ALTER TABLE [dbo].[denuncia]  WITH CHECK ADD  CONSTRAINT [FK_denunciador_utilizador] FOREIGN KEY([id_denunciador])
REFERENCES [dbo].[utilizador] ([id])
GO

ALTER TABLE [dbo].[denuncia] CHECK CONSTRAINT [FK_denunciador_utilizador]
GO


																	/****** SALA ******/


SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[sala](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[estado] [int] NOT NULL,
	[titulo] [nvarchar](50) NOT NULL,
	[descricao] [nvarchar](200) NULL,
	[id_comprador] [int] NOT NULL,
 CONSTRAINT [PK_sala] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[sala]  WITH CHECK ADD  CONSTRAINT [FK_sala_comprador] FOREIGN KEY([id_comprador])
REFERENCES [dbo].[comprador] ([id])
GO

ALTER TABLE [dbo].[sala] CHECK CONSTRAINT [FK_sala_comprador]
GO




																	/****** CHAT ******/


SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[chat](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[data] [datetime] NOT NULL,
	[mensagem] [nvarchar](1000) NOT NULL,
	[id_utilizador] [int] NOT NULL,
	[id_sala] [int] NOT NULL,
 CONSTRAINT [PK_chat] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[chat]  WITH CHECK ADD  CONSTRAINT [FK_chat_sala] FOREIGN KEY([id_sala])
REFERENCES [dbo].[sala] ([id])
GO

ALTER TABLE [dbo].[chat] CHECK CONSTRAINT [FK_chat_sala]
GO

ALTER TABLE [dbo].[chat]  WITH CHECK ADD  CONSTRAINT [FK_chat_utilizador] FOREIGN KEY([id_utilizador])
REFERENCES [dbo].[utilizador] ([id])
GO

ALTER TABLE [dbo].[chat] CHECK CONSTRAINT [FK_chat_utilizador]
GO


																	/****** VENDA ******/


SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[venda](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_vendedor] [int] NOT NULL,
	[payment_method] [nvarchar](20) NOT NULL,
	[date] [datetime] NOT NULL,
	[verified] [bit] NOT NULL,
	[value] [float] NOT NULL,
	[id_sala] [int] NOT NULL,
 CONSTRAINT [PK_venda] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[venda]  WITH CHECK ADD  CONSTRAINT [FK_venda_sala] FOREIGN KEY([id_sala])
REFERENCES [dbo].[sala] ([id])
GO

ALTER TABLE [dbo].[venda]  WITH CHECK ADD  CONSTRAINT [FK_venda_vendedor] FOREIGN KEY([id_vendedor])
REFERENCES [dbo].[vendedor] ([id])
GO


ALTER TABLE [dbo].[venda] CHECK CONSTRAINT [FK_venda_sala]
GO


																	/****** VENDEDOR_HAS_SALA ******/

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[vendedor_has_sala](
	[id_vendedor] [int] NOT NULL,
	[id_sala] [int] NOT NULL
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[vendedor_has_sala]  WITH CHECK ADD  CONSTRAINT [FK_vendedor_has_sala_sala] FOREIGN KEY([id_sala])
REFERENCES [dbo].[sala] ([id])
GO

ALTER TABLE [dbo].[vendedor_has_sala] CHECK CONSTRAINT [FK_vendedor_has_sala_sala]
GO

ALTER TABLE [dbo].[vendedor_has_sala]  WITH CHECK ADD  CONSTRAINT [FK_vendedor_has_sala_vendedor] FOREIGN KEY([id_vendedor])
REFERENCES [dbo].[vendedor] ([id])
GO

ALTER TABLE [dbo].[vendedor_has_sala] CHECK CONSTRAINT [FK_vendedor_has_sala_vendedor]
GO


