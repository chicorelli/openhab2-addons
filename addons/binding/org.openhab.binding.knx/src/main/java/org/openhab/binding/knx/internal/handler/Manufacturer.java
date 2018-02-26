/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.knx.internal.handler;

import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * Enumeration containing all the KNX device manufactureres.
 *
 * @author Karel Goderis - Initial contribution
 */
@NonNullByDefault
public enum Manufacturer {
    M1(1, "Siemens"),
    M2(2, "ABB"),
    M4(4, "Albrecht Jung"),
    M5(5, "Bticino"),
    M6(6, "Berker"),
    M7(7, "Busch-Jaeger Elektro"),
    M8(8, "GIRA Giersiepen"),
    M9(9, "Hager Electro"),
    M10(10, "INSTA ELEKTRO"),
    M11(11, "LEGRAND Appareillage électrique"),
    M12(12, "Merten"),
    M14(14, "ABB SpA – SACE Division"),
    M22(22, "Siedle & Söhne"),
    M24(24, "Eberle"),
    M25(25, "GEWISS"),
    M27(27, "Albert Ackermann"),
    M28(28, "Schupa GmbH"),
    M29(29, "ABB SCHWEIZ"),
    M30(30, "Feller"),
    M32(32, "DEHN & SÖHNE"),
    M33(33, "CRABTREE"),
    M36(36, "Paul Hochköpper"),
    M37(37, "Altenburger Electronic"),
    M41(41, "Grässlin"),
    M42(42, "Simon"),
    M44(44, "VIMAR"),
    M45(45, "Moeller Gebäudeautomation KG"),
    M46(46, "Eltako"),
    M49(49, "Bosch-Siemens Haushaltsgeräte"),
    M52(52, "RITTO GmbH&Co.KG"),
    M53(53, "Power Controls"),
    M55(55, "ZUMTOBEL"),
    M57(57, "Phoenix Contact"),
    M61(61, "WAGO Kontakttechnik"),
    M66(66, "Wieland Electric"),
    M67(67, "Hermann Kleinhuis"),
    M69(69, "Stiebel Eltron"),
    M71(71, "Tehalit"),
    M72(72, "Theben AG"),
    M73(73, "Wilhelm Rutenbeck"),
    M75(75, "Winkhaus"),
    M76(76, "Robert Bosch"),
    M78(78, "Somfy"),
    M80(80, "Woertz"),
    M81(81, "Viessmann Werke"),
    M82(82, "Theodor HEIMEIER Metallwerk"),
    M83(83, "Joh. Vaillant"),
    M85(85, "AMP Deutschland"),
    M89(89, "Bosch Thermotechnik GmbH"),
    M90(90, "SEF - ECOTEC"),
    M92(92, "DORMA GmbH + Co. KG"),
    M93(93, "WindowMaster A/S"),
    M94(94, "Walther Werke"),
    M95(95, "ORAS"),
    M97(97, "Dätwyler"),
    M98(98, "Electrak"),
    M99(99, "Techem"),
    M100(100, "Schneider Electric Industries SAS"),
    M101(101, "WHD Wilhelm Huber + Söhne"),
    M102(102, "Bischoff Elektronik"),
    M104(104, "JEPAZ"),
    M105(105, "RTS Automation"),
    M106(106, "EIBMARKT GmbH"),
    M107(107, "WAREMA electronic GmbH"),
    M108(108, "Eelectron"),
    M109(109, "Belden Wire & Cable B.V."),
    M110(110, "Becker-Antriebe GmbH"),
    M111(111, "J.Stehle+Söhne GmbH"),
    M112(112, "AGFEO"),
    M113(113, "Zennio"),
    M114(114, "TAPKO Technologies"),
    M115(115, "HDL"),
    M116(116, "Uponor"),
    M117(117, "se Lightmanagement AG"),
    M118(118, "Arcus-eds"),
    M119(119, "Intesis"),
    M120(120, "Herholdt Controls srl"),
    M121(121, "Zublin AG"),
    M122(122, "Durable Technologies"),
    M123(123, "Innoteam"),
    M124(124, "ise GmbH"),
    M125(125, "TEAM FOR TRONICS"),
    M126(126, "CIAT"),
    M127(127, "Remeha BV"),
    M128(128, "ESYLUX"),
    M129(129, "BASALTE"),
    M130(130, "Vestamatic"),
    M131(131, "MDT technologies"),
    M132(132, "Warendorfer Küchen GmbH"),
    M133(133, "Video-Star"),
    M134(134, "Sitek"),
    M135(135, "CONTROLtronic"),
    M136(136, "function Technology"),
    M137(137, "AMX"),
    M138(138, "ELDAT"),
    M139(139, "VIKO"),
    M140(140, "Pulse Technologies"),
    M141(141, "Crestron"),
    M142(142, "STEINEL professional"),
    M143(143, "BILTON LED Lighting"),
    M144(144, "denro AG"),
    M145(145, "GePro"),
    M146(146, "preussen automation"),
    M147(147, "Zoppas Industries"),
    M148(148, "MACTECH"),
    M149(149, "TECHNO-TREND"),
    M150(150, "FS Cables"),
    M151(151, "Delta Dore"),
    M152(152, "Eissound"),
    M153(153, "Cisco"),
    M154(154, "Dinuy"),
    M155(155, "iKNiX"),
    M156(156, "Rademacher Geräte-Elektronik GmbH & Co. KG"),
    M157(157, "EGi Electroacustica General Iberica"),
    M158(158, "Ingenium"),
    M159(159, "ElabNET"),
    M160(160, "Blumotix"),
    M161(161, "Hunter Douglas"),
    M162(162, "APRICUM"),
    M163(163, "TIANSU Automation"),
    M164(164, "Bubendorff"),
    M165(165, "MBS GmbH"),
    M166(166, "Enertex Bayern GmbH"),
    M167(167, "BMS"),
    M168(168, "Sinapsi"),
    M169(169, "Embedded Systems SIA"),
    M170(170, "KNX1"),
    M171(171, "Tokka"),
    M172(172, "NanoSense"),
    M173(173, "PEAR Automation GmbH"),
    M174(174, "DGA"),
    M175(175, "Lutron"),
    M176(176, "AIRZONE – ALTRA"),
    M177(177, "Lithoss Design Switches"),
    M178(178, "3ATEL"),
    M179(179, "Philips Controls"),
    M180(180, "VELUX A/S"),
    M181(181, "LOYTEC"),
    M182(182, "SBS S.p.A."),
    M183(183, "SIRLAN Technologies"),
    M184(184, "Bleu Comm' Azur"),
    M185(185, "IT GmbH"),
    M186(186, "RENSON"),
    M187(187, "HEP Group"),
    M188(188, "Balmart"),
    M189(189, "GFS GmbH"),
    M190(190, "Schenker Storen AG"),
    M191(191, "Algodue Elettronica S.r.L."),
    M192(192, "Newron System"),
    M193(193, "maintronic"),
    M194(194, "Vantage"),
    M195(195, "Foresis"),
    M196(196, "Research & Production Association SEM"),
    M197(197, "Weinzierl Engineering GmbH"),
    M198(198, "Möhlenhoff Wärmetechnik GmbH"),
    M199(199, "PKC-GROUP Oyj"),
    M200(200, "B.E.G."),
    M201(201, "Elsner Elektronik GmbH"),
    M202(202, "Siemens Building Technologies (HK/China) Ltd."),
    M204(204, "Eutrac"),
    M205(205, "Gustav Hensel GmbH & Co. KG"),
    M206(206, "GARO AB"),
    M207(207, "Waldmann Lichttechnik"),
    M208(208, "SCHÜCO"),
    M209(209, "EMU"),
    M210(210, "JNet Systems AG"),
    M214(214, "O.Y.L. Electronics"),
    M215(215, "Galax System"),
    M216(216, "Disch"),
    M217(217, "Aucoteam"),
    M218(218, "Luxmate Controls"),
    M219(219, "Danfoss"),
    M220(220, "AST GmbH"),
    M222(222, "WILA Leuchten"),
    M223(223, "b+b Automations- und Steuerungstechnik"),
    M225(225, "Lingg & Janke"),
    M227(227, "Sauter"),
    M228(228, "SIMU"),
    M232(232, "Theben HTS AG"),
    M233(233, "Amann GmbH"),
    M234(234, "BERG Energiekontrollsysteme GmbH"),
    M235(235, "Hüppe Form Sonnenschutzsysteme GmbH"),
    M237(237, "Oventrop KG"),
    M238(238, "Griesser AG"),
    M239(239, "IPAS GmbH"),
    M240(240, "elero GmbH"),
    M241(241, "Ardan Production and Industrial Controls Ltd."),
    M242(242, "Metec Meßtechnik GmbH"),
    M244(244, "ELKA-Elektronik GmbH"),
    M245(245, "ELEKTROANLAGEN D. NAGEL"),
    M246(246, "Tridonic Bauelemente GmbH"),
    M248(248, "Stengler Gesellschaft"),
    M249(249, "Schneider Electric (MG)"),
    M250(250, "KNX Association"),
    M251(251, "VIVO"),
    M252(252, "Hugo Müller GmbH & Co KG"),
    M253(253, "Siemens HVAC"),
    M254(254, "APT"),
    M256(256, "HighDom"),
    M257(257, "Top Services"),
    M258(258, "ambiHome"),
    M259(259, "DATEC electronic AG"),
    M260(260, "ABUS Security-Center"),
    M261(261, "Lite-Puter"),
    M262(262, "Tantron Electronic"),
    M263(263, "Yönnet"),
    M264(264, "DKX Tech"),
    M265(265, "Viatron"),
    M266(266, "Nautibus"),
    M268(268, "Longchuang"),
    M269(269, "Air-On AG"),
    M270(270, "ib-company GmbH"),
    M271(271, "SATION"),
    M272(272, "Agentilo GmbH"),
    M273(273, "Makel Elektrik"),
    M274(274, "Helios Ventilatoren"),
    M275(275, "Otto Solutions Pte Ltd"),
    M276(276, "Airmaster"),
    M277(277, "HEINEMANN GmbH"),
    M278(278, "LDS"),
    M279(279, "ASIN"),
    M280(280, "Bridges"),
    M281(281, "ARBONIA"),
    M282(282, "KERMI"),
    M283(283, "PROLUX"),
    M284(284, "ClicHome"),
    M285(285, "COMMAX"),
    M286(286, "EAE"),
    M287(287, "Tense"),
    M288(288, "Seyoung Electronics"),
    M289(289, "Lifedomus"),
    M290(290, "EUROtronic Technology GmbH"),
    M291(291, "tci"),
    M292(292, "Rishun Electronic"),
    M293(293, "Zipato"),
    M294(294, "cm-security GmbH & Co KG"),
    M295(295, "Qing Cables"),
    M296(296, "LABIO"),
    M297(297, "Coster Tecnologie Elettroniche S.p.A."),
    M298(298, "E.G.E"),
    M299(299, "NETxAutomation"),
    M300(300, "tecalor"),
    M301(301, "Urmet Electronics (Huizhou) Ltd."),
    M302(302, "Peiying Building Control"),
    M303(303, "BPT S.p.A. a Socio Unico"),
    M304(304, "Kanontec - KanonBUS"),
    M305(305, "ISER Tech"),
    M306(306, "Fineline"),
    M307(307, "CP Electronics Ltd"),
    M308(308, "Servodan A/S"),
    M309(309, "Simon"),
    M310(310, "GM modular pvt. Ltd."),
    M311(311, "FU CHENG Intelligence"),
    M312(312, "NexKon"),
    M313(313, "FEEL s.r.l"),
    M314(314, "Not Assigned"),
    M315(315, "Shenzhen Fanhai Sanjiang Electronics Co., Ltd."),
    M316(316, "Jiuzhou Greeble"),
    M317(317, "Aumüller Aumatic GmbH"),
    M318(318, "Etman Electric"),
    M319(319, "EMT Controls"),
    M320(320, "ZidaTech AG"),
    M321(321, "IDGS bvba"),
    M322(322, "dakanimo"),
    M323(323, "Trebor Automation AB"),
    M324(324, "Satel sp. z o.o."),
    M325(325, "Russound, Inc."),
    M326(326, "Midea Heating & Ventilating Equipment CO LTD"),
    M327(327, "Consorzio Terranuova"),
    M328(328, "Wolf Heiztechnik GmbH"),
    M329(329, "SONTEC"),
    M330(330, "Belcom Cables Ltd."),
    M331(331, "Guangzhou SeaWin Electrical Technologies Co., Ltd."),
    M332(332, "Acrel"),
    M333(333, "Franke Aquarotter GmbH"),
    M334(334, "Orion Systems"),
    M335(335, "Schrack Technik GmbH"),
    M336(336, "INSPRID"),
    M337(337, "Sunricher"),
    M338(338, "Menred automation system(shanghai) Co.,Ltd."),
    M339(339, "Aurex"),
    M340(340, "Josef Barthelme GmbH & Co. KG"),
    M341(341, "Architecture Numerique"),
    M342(342, "UP GROUP"),
    M343(343, "Teknos-Avinno"),
    M344(344, "Ningbo Dooya Mechanic & Electronic Technology"),
    M345(345, "Thermokon Sensortechnik GmbH"),
    M346(346, "BELIMO Automation AG"),
    M347(347, "Zehnder Group International AG"),
    M348(348, "sks Kinkel Elektronik"),
    M349(349, "ECE Wurmitzer GmbH"),
    M350(350, "LARS"),
    M351(351, "URC"),
    M352(352, "LightControl"),
    M353(353, "ShenZhen YM"),
    M354(354, "MEAN WELL Enterprises Co. Ltd."),
    M355(355, "OSix"),
    M356(356, "AYPRO Technology"),
    M357(357, "Hefei Ecolite Software"),
    M358(358, "Enno"),
    M359(359, "Ohosure");

    private int code;
    private String name;

    private Manufacturer(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static String getName(int code) {
        for (Manufacturer c : Manufacturer.values()) {
            if (c.code == code) {
                return c.name;
            }
        }
        return "Unknown";
    }
}
