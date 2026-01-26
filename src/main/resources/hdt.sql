--
-- PostgreSQL database dump
--

\restrict fbQGd66emIl09VxhHbXDjsJShAQcYz4YuzxUOAXqQ2hHlAqadVatcSxS050VLwH

-- Dumped from database version 18.0
-- Dumped by pg_dump version 18.0

-- Started on 2026-01-13 19:06:09

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 241 (class 1255 OID 16530)
-- Name: validate_interface_ip(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.validate_interface_ip() RETURNS trigger
    LANGUAGE plpgsql
    AS $_$
BEGIN
    -- Validazione formato IP base
    IF NEW.ipaddress !~ '^[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}$' THEN
        RAISE EXCEPTION 'Formato indirizzo IP non valido';
    END IF;
    
    -- Validazione range IP (0-255 per ogni ottetto)
    IF (
        CAST(split_part(NEW.ipaddress, '.', 1) AS INTEGER) > 255 OR
        CAST(split_part(NEW.ipaddress, '.', 2) AS INTEGER) > 255 OR
        CAST(split_part(NEW.ipaddress, '.', 3) AS INTEGER) > 255 OR
        CAST(split_part(NEW.ipaddress, '.', 4) AS INTEGER) > 255
    ) THEN
        RAISE EXCEPTION 'Valori IP devono essere compresi tra 0 e 255';
    END IF;
    
    RETURN NEW;
END;
$_$;


ALTER FUNCTION public.validate_interface_ip() OWNER TO postgres;

--
-- TOC entry 239 (class 1255 OID 16533)
-- Name: validate_property_description(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.validate_property_description() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF NEW.description IS NOT NULL AND LENGTH(NEW.description) < 10 THEN
        RAISE EXCEPTION 'Descrizione deve essere lunga almeno 10 caratteri';
    END IF;
    RETURN NEW;
END;
$$;


ALTER FUNCTION public.validate_property_description() OWNER TO postgres;

--
-- TOC entry 240 (class 1255 OID 16536)
-- Name: validate_time_dates(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.validate_time_dates() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    -- Validazione esclusività date
    IF NOT (
        (NEW.dateEnter IS NOT NULL AND NEW.dateStart IS NULL AND NEW.dateEnd IS NULL) OR
        (NEW.dateEnter IS NULL AND NEW.dateStart IS NOT NULL AND NEW.dateEnd IS NOT NULL)
    ) THEN
        RAISE EXCEPTION 'Deve essere specificato o dateEnter o entrambe dateStart e dateEnd';
    END IF;

    -- Validazione dateEnter non nel futuro (solo per INSERT)
    IF TG_OP = 'INSERT' AND NEW.dateEnter IS NOT NULL AND NEW.dateEnter > NOW() THEN
        RAISE EXCEPTION 'dateEnter non può essere nel futuro';
    END IF;
    
    RETURN NEW;
END;
$$;


ALTER FUNCTION public.validate_time_dates() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 228 (class 1259 OID 16540)
-- Name: associated; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.associated (
    id integer NOT NULL,
    property_id integer NOT NULL,
    interface_id integer NOT NULL
);


ALTER TABLE public.associated OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 16539)
-- Name: associated_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.associated_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.associated_id_seq OWNER TO postgres;

--
-- TOC entry 5068 (class 0 OID 0)
-- Dependencies: 227
-- Name: associated_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.associated_id_seq OWNED BY public.associated.id;


--
-- TOC entry 232 (class 1259 OID 16584)
-- Name: defines; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.defines (
    id integer NOT NULL,
    value_id integer NOT NULL,
    property_id integer NOT NULL
);


ALTER TABLE public.defines OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 16583)
-- Name: defines_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.defines_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.defines_id_seq OWNER TO postgres;

--
-- TOC entry 5069 (class 0 OID 0)
-- Dependencies: 231
-- Name: defines_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.defines_id_seq OWNED BY public.defines.id;


--
-- TOC entry 238 (class 1259 OID 24581)
-- Name: humandigitaltwin; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.humandigitaltwin (
    id integer NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE public.humandigitaltwin OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 24580)
-- Name: humandigitaltwin_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.humandigitaltwin_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.humandigitaltwin_id_seq OWNER TO postgres;

--
-- TOC entry 5070 (class 0 OID 0)
-- Dependencies: 237
-- Name: humandigitaltwin_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.humandigitaltwin_id_seq OWNED BY public.humandigitaltwin.id;


--
-- TOC entry 234 (class 1259 OID 16606)
-- Name: implements; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.implements (
    id integer NOT NULL,
    property_id integer NOT NULL,
    humandigitaltwin_id integer NOT NULL
);


ALTER TABLE public.implements OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 16605)
-- Name: implements_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.implements_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.implements_id_seq OWNER TO postgres;

--
-- TOC entry 5071 (class 0 OID 0)
-- Dependencies: 233
-- Name: implements_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.implements_id_seq OWNED BY public.implements.id;


--
-- TOC entry 236 (class 1259 OID 16628)
-- Name: interacts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.interacts (
    id integer NOT NULL,
    interface_id integer NOT NULL,
    humandigitaltwin_id integer NOT NULL
);


ALTER TABLE public.interacts OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 16627)
-- Name: interacts_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.interacts_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.interacts_id_seq OWNER TO postgres;

--
-- TOC entry 5072 (class 0 OID 0)
-- Dependencies: 235
-- Name: interacts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.interacts_id_seq OWNED BY public.interacts.id;


--
-- TOC entry 220 (class 1259 OID 16399)
-- Name: interface; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.interface (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    ipaddress character varying(15) CONSTRAINT interface_ipadress_not_null NOT NULL,
    port integer NOT NULL,
    clientid character varying(50) NOT NULL,
    type character varying(50) NOT NULL
);


ALTER TABLE public.interface OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16398)
-- Name: interface_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.interface_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.interface_id_seq OWNER TO postgres;

--
-- TOC entry 5073 (class 0 OID 0)
-- Dependencies: 219
-- Name: interface_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.interface_id_seq OWNED BY public.interface.id;


--
-- TOC entry 222 (class 1259 OID 16414)
-- Name: property; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.property (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    description text
);


ALTER TABLE public.property OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16413)
-- Name: property_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.property_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.property_id_seq OWNER TO postgres;

--
-- TOC entry 5074 (class 0 OID 0)
-- Dependencies: 221
-- Name: property_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.property_id_seq OWNED BY public.property.id;


--
-- TOC entry 230 (class 1259 OID 16562)
-- Name: sampling; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sampling (
    id integer NOT NULL,
    time_id integer NOT NULL,
    value_id integer NOT NULL
);


ALTER TABLE public.sampling OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 16561)
-- Name: sampling_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sampling_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sampling_id_seq OWNER TO postgres;

--
-- TOC entry 5075 (class 0 OID 0)
-- Dependencies: 229
-- Name: sampling_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sampling_id_seq OWNED BY public.sampling.id;


--
-- TOC entry 224 (class 1259 OID 16427)
-- Name: time; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."time" (
    id integer NOT NULL,
    dateenter timestamp without time zone,
    datestart timestamp without time zone,
    dateend timestamp without time zone
);


ALTER TABLE public."time" OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16426)
-- Name: time_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.time_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.time_id_seq OWNER TO postgres;

--
-- TOC entry 5076 (class 0 OID 0)
-- Dependencies: 223
-- Name: time_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.time_id_seq OWNED BY public."time".id;


--
-- TOC entry 226 (class 1259 OID 16435)
-- Name: value; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.value (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    value character varying(255) NOT NULL,
    type character varying(50) NOT NULL
);


ALTER TABLE public.value OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16434)
-- Name: value_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.value_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.value_id_seq OWNER TO postgres;

--
-- TOC entry 5077 (class 0 OID 0)
-- Dependencies: 225
-- Name: value_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.value_id_seq OWNED BY public.value.id;


--
-- TOC entry 4861 (class 2604 OID 16543)
-- Name: associated id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.associated ALTER COLUMN id SET DEFAULT nextval('public.associated_id_seq'::regclass);


--
-- TOC entry 4863 (class 2604 OID 16587)
-- Name: defines id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.defines ALTER COLUMN id SET DEFAULT nextval('public.defines_id_seq'::regclass);


--
-- TOC entry 4866 (class 2604 OID 24584)
-- Name: humandigitaltwin id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.humandigitaltwin ALTER COLUMN id SET DEFAULT nextval('public.humandigitaltwin_id_seq'::regclass);


--
-- TOC entry 4864 (class 2604 OID 16609)
-- Name: implements id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.implements ALTER COLUMN id SET DEFAULT nextval('public.implements_id_seq'::regclass);


--
-- TOC entry 4865 (class 2604 OID 16631)
-- Name: interacts id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interacts ALTER COLUMN id SET DEFAULT nextval('public.interacts_id_seq'::regclass);


--
-- TOC entry 4857 (class 2604 OID 16402)
-- Name: interface id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interface ALTER COLUMN id SET DEFAULT nextval('public.interface_id_seq'::regclass);


--
-- TOC entry 4858 (class 2604 OID 16417)
-- Name: property id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.property ALTER COLUMN id SET DEFAULT nextval('public.property_id_seq'::regclass);


--
-- TOC entry 4862 (class 2604 OID 16565)
-- Name: sampling id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sampling ALTER COLUMN id SET DEFAULT nextval('public.sampling_id_seq'::regclass);


--
-- TOC entry 4859 (class 2604 OID 16430)
-- Name: time id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."time" ALTER COLUMN id SET DEFAULT nextval('public.time_id_seq'::regclass);


--
-- TOC entry 4860 (class 2604 OID 16438)
-- Name: value id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.value ALTER COLUMN id SET DEFAULT nextval('public.value_id_seq'::regclass);


--
-- TOC entry 4880 (class 2606 OID 16548)
-- Name: associated associated_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.associated
    ADD CONSTRAINT associated_pkey PRIMARY KEY (id);


--
-- TOC entry 4882 (class 2606 OID 16550)
-- Name: associated associated_property_id_interface_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.associated
    ADD CONSTRAINT associated_property_id_interface_id_key UNIQUE (property_id, interface_id);


--
-- TOC entry 4888 (class 2606 OID 16592)
-- Name: defines defines_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.defines
    ADD CONSTRAINT defines_pkey PRIMARY KEY (id);


--
-- TOC entry 4890 (class 2606 OID 16594)
-- Name: defines defines_value_id_property_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.defines
    ADD CONSTRAINT defines_value_id_property_id_key UNIQUE (value_id, property_id);


--
-- TOC entry 4900 (class 2606 OID 24588)
-- Name: humandigitaltwin humandigitaltwin_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.humandigitaltwin
    ADD CONSTRAINT humandigitaltwin_pkey PRIMARY KEY (id);


--
-- TOC entry 4892 (class 2606 OID 16614)
-- Name: implements implements_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.implements
    ADD CONSTRAINT implements_pkey PRIMARY KEY (id);


--
-- TOC entry 4894 (class 2606 OID 16616)
-- Name: implements implements_property_id_humandigitaltwin_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.implements
    ADD CONSTRAINT implements_property_id_humandigitaltwin_id_key UNIQUE (property_id, humandigitaltwin_id);


--
-- TOC entry 4896 (class 2606 OID 16638)
-- Name: interacts interacts_interface_id_humandigitaltwin_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interacts
    ADD CONSTRAINT interacts_interface_id_humandigitaltwin_id_key UNIQUE (interface_id, humandigitaltwin_id);


--
-- TOC entry 4898 (class 2606 OID 16636)
-- Name: interacts interacts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interacts
    ADD CONSTRAINT interacts_pkey PRIMARY KEY (id);


--
-- TOC entry 4868 (class 2606 OID 16410)
-- Name: interface interface_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interface
    ADD CONSTRAINT interface_pkey PRIMARY KEY (id);


--
-- TOC entry 4872 (class 2606 OID 16425)
-- Name: property property_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.property
    ADD CONSTRAINT property_name_key UNIQUE (name);


--
-- TOC entry 4874 (class 2606 OID 16423)
-- Name: property property_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.property
    ADD CONSTRAINT property_pkey PRIMARY KEY (id);


--
-- TOC entry 4884 (class 2606 OID 16570)
-- Name: sampling sampling_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sampling
    ADD CONSTRAINT sampling_pkey PRIMARY KEY (id);


--
-- TOC entry 4886 (class 2606 OID 16572)
-- Name: sampling sampling_time_id_value_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sampling
    ADD CONSTRAINT sampling_time_id_value_id_key UNIQUE (time_id, value_id);


--
-- TOC entry 4876 (class 2606 OID 16433)
-- Name: time time_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."time"
    ADD CONSTRAINT time_pkey PRIMARY KEY (id);


--
-- TOC entry 4870 (class 2606 OID 16412)
-- Name: interface unique_ip_port; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interface
    ADD CONSTRAINT unique_ip_port UNIQUE (ipaddress, port);


--
-- TOC entry 4878 (class 2606 OID 16444)
-- Name: value value_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.value
    ADD CONSTRAINT value_pkey PRIMARY KEY (id);


--
-- TOC entry 4910 (class 2620 OID 16531)
-- Name: interface trg_interface_validation_insert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trg_interface_validation_insert BEFORE INSERT ON public.interface FOR EACH ROW EXECUTE FUNCTION public.validate_interface_ip();


--
-- TOC entry 4911 (class 2620 OID 16532)
-- Name: interface trg_interface_validation_update; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trg_interface_validation_update BEFORE UPDATE ON public.interface FOR EACH ROW EXECUTE FUNCTION public.validate_interface_ip();


--
-- TOC entry 4912 (class 2620 OID 16534)
-- Name: property trg_property_validation_insert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trg_property_validation_insert BEFORE INSERT ON public.property FOR EACH ROW EXECUTE FUNCTION public.validate_property_description();


--
-- TOC entry 4913 (class 2620 OID 16535)
-- Name: property trg_property_validation_update; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trg_property_validation_update BEFORE UPDATE ON public.property FOR EACH ROW EXECUTE FUNCTION public.validate_property_description();


--
-- TOC entry 4914 (class 2620 OID 16537)
-- Name: time trg_time_validation_insert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trg_time_validation_insert BEFORE INSERT ON public."time" FOR EACH ROW EXECUTE FUNCTION public.validate_time_dates();


--
-- TOC entry 4915 (class 2620 OID 16538)
-- Name: time trg_time_validation_update; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER trg_time_validation_update BEFORE UPDATE ON public."time" FOR EACH ROW EXECUTE FUNCTION public.validate_time_dates();


--
-- TOC entry 4901 (class 2606 OID 16556)
-- Name: associated associated_interface_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.associated
    ADD CONSTRAINT associated_interface_id_fkey FOREIGN KEY (interface_id) REFERENCES public.interface(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4902 (class 2606 OID 16551)
-- Name: associated associated_property_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.associated
    ADD CONSTRAINT associated_property_id_fkey FOREIGN KEY (property_id) REFERENCES public.property(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4905 (class 2606 OID 16600)
-- Name: defines defines_property_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.defines
    ADD CONSTRAINT defines_property_id_fkey FOREIGN KEY (property_id) REFERENCES public.property(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4906 (class 2606 OID 16595)
-- Name: defines defines_value_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.defines
    ADD CONSTRAINT defines_value_id_fkey FOREIGN KEY (value_id) REFERENCES public.value(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4907 (class 2606 OID 24592)
-- Name: implements fk_implements_humandigitaltwin_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.implements
    ADD CONSTRAINT fk_implements_humandigitaltwin_id FOREIGN KEY (humandigitaltwin_id) REFERENCES public.humandigitaltwin(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 4908 (class 2606 OID 16617)
-- Name: implements implements_property_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.implements
    ADD CONSTRAINT implements_property_id_fkey FOREIGN KEY (property_id) REFERENCES public.property(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4909 (class 2606 OID 16639)
-- Name: interacts interacts_interface_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.interacts
    ADD CONSTRAINT interacts_interface_id_fkey FOREIGN KEY (interface_id) REFERENCES public.interface(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4903 (class 2606 OID 16573)
-- Name: sampling sampling_time_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sampling
    ADD CONSTRAINT sampling_time_id_fkey FOREIGN KEY (time_id) REFERENCES public."time"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 4904 (class 2606 OID 16578)
-- Name: sampling sampling_value_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sampling
    ADD CONSTRAINT sampling_value_id_fkey FOREIGN KEY (value_id) REFERENCES public.value(id) ON UPDATE CASCADE ON DELETE CASCADE;


-- Completed on 2026-01-13 19:06:09

--
-- PostgreSQL database dump complete
--

\unrestrict fbQGd66emIl09VxhHbXDjsJShAQcYz4YuzxUOAXqQ2hHlAqadVatcSxS050VLwH

